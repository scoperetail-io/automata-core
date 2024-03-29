package com.scoperetail.automata.core.fsm;

/*-
 * *****
 * automata-core
 * -----
 * Copyright (C) 2018 - 2021 Scope Retail Systems Inc.
 * -----
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * =====
 */

import com.scoperetail.automata.core.annotations.Automata;
import com.scoperetail.automata.core.annotations.Preaction;
import com.scoperetail.automata.core.annotations.Precondition;
import com.scoperetail.automata.core.exception.DisconnectedGraphException;
import com.scoperetail.automata.core.exception.StateAutomataException;
import com.scoperetail.automata.core.helper.GraphTheoryHelper;
import com.scoperetail.automata.core.persistence.entity.PendingEvent;
import com.scoperetail.automata.core.service.EventService;
import org.springframework.context.ApplicationContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/** @author scoperetail */
public class FSMHarness {

  private FSMHarness() {}

  // For maintaining cohesiveness
  @SuppressWarnings("squid:S3776")
  public static void harnessFSM(
      FSMCollection fsmCollection,
      ApplicationContext applicationContext,
      EventService eventService,
      Class<?> automataClass)
      throws DisconnectedGraphException, StateAutomataException {

    FSM fsm = new FSM();
    Class<?> clazz = automataClass;
    if (clazz.isAnnotationPresent(Automata.class)) {
      Annotation annotation = clazz.getAnnotation(Automata.class);
      Automata automata = (Automata) annotation;

      String[] endStates = automata.endState();
      List<String> endStateList = Arrays.asList(endStates);
      String startState = automata.startState();
      String fsmName = automata.name();

      fsm.setEventService(eventService);
      fsm.setName(fsmName);

      fsm.setClazz(automata.clazz());
      fsm.setServiceImpl((FSMService) applicationContext.getBean(automata.service()));
      fsm.setAutomata(applicationContext.getBean(automata.name()));
      for (com.scoperetail.automata.core.annotations.Event event : automata.events()) {
        PendingEvent e = new PendingEvent();
        e.setEventName(event.name());
        fsm.getEvents().put(e.getEventName(), e);
      }

      for (com.scoperetail.automata.core.annotations.State state : automata.states()) {
        State s = new State();
        s.setName(state.name());
        s.setComment(state.comment());
        if (startState.equalsIgnoreCase(state.name())) {
          s.setInitState(true);
        }
        if (endStateList.contains(state.name())) {
          s.setEndState(true);
        }
        fsm.getStates().put(s.getName(), s);
      }

      for (com.scoperetail.automata.core.annotations.Transition transition :
          automata.transitions()) {
        String fromName = transition.from();
        String toName = transition.to();
        String eventName = transition.event();
        State fromState = fsm.getStates().get(fromName);
        // Check for transitions with no matching nodes, if yes StateAutomataException
        if (fromState == null) {
          throw new StateAutomataException(
              automataClass.getName()
                  + " :State used in @Transition :"
                  + fromName
                  + "is not defined in @State");
        }

        PendingEvent onEvent = fsm.getEvents().get(eventName);
        // Check for events with no matching nodes, if yes StateAutomataException
        if (onEvent == null) {
          throw new StateAutomataException(
              automataClass.getName()
                  + " :PendingEvent used in @Transition :"
                  + eventName
                  + "is not defined in @PendingEvent");
        }
        State toState = fsm.getStates().get(toName);
        // Check for transitions with no matching nodes, if yes StateAutomataException
        if (toState == null) {
          throw new StateAutomataException(
              automataClass.getName()
                  + " :State used in @Transition :\""
                  + toName
                  + "\" is not defined in @State");
        }

        fromState.addTransition(onEvent.getEventName(), toState);
        fromState.getValidEvents().add(onEvent.getEventName());
      }

      if (fsm.getStates().containsKey(startState)) {
        fsm.setStartState(startState);
      } else {
        throw new StateAutomataException(
            automataClass.getName() + " :Start State is not defined or is not present in @State");
      }

      boolean foundEndState = false;
      for (String endState : endStates) {
        if (fsm.getStates().containsKey(endState)) {
          fsm.addEndState(endState);
          foundEndState = true;
        }
      }

      if (!foundEndState) {
        throw new StateAutomataException(
            automataClass.getName() + " :End State is not defined or is not present in @State");
      }

      // Graph Theory Validations
      // Check if the graph is disconnected, No 2 sets of nodes should be disconnected, and all
      // nodes should be reachable from start state

      // Populate Future events for every State, this will help in discarding events which are no
      // longer valid for automata
      // such events could result due to redelivery or late arrivals
      if (GraphTheoryHelper.checkConnectivity(fsm)) {
        fsmCollection.addFSMByName(fsmName, fsm);
      } else {
        // if yes throw exception due to Disconnected Graph
        throw new DisconnectedGraphException(
            automataClass.getName() + ":Disconnected Graph not supported by olcm");
      }
    }

    for (Method method : clazz.getDeclaredMethods()) {
      if (method.isAnnotationPresent(Precondition.class)) {
        Annotation annotation = method.getAnnotation(Precondition.class);
        Precondition precondition = (Precondition) annotation;
        com.scoperetail.automata.core.annotations.Transition transition = precondition.transition();
        String fromName = transition.from();
        String eventName = transition.event();
        State fromState = fsm.getStates().get(fromName);
        PendingEvent onEvent = fsm.getEvents().get(eventName);
        fromState.addPreCondition(onEvent.getEventName(), method);
      }

      if (method.isAnnotationPresent(Preaction.class)) {
        Annotation annotation = method.getAnnotation(Preaction.class);
        Preaction preaction = (Preaction) annotation;
        com.scoperetail.automata.core.annotations.Transition transition = preaction.transition();
        String fromName = transition.from();
        String eventName = transition.event();
        State fromState = fsm.getStates().get(fromName);
        PendingEvent onEvent = fsm.getEvents().get(eventName);
        fromState.addPreAction(onEvent.getEventName(), method);
      }
    }
  }
}

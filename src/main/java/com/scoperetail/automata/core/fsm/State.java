package com.scoperetail.automata.core.fsm;

import com.scoperetail.automata.core.persistence.entity.PendingEvent;

import java.lang.reflect.Method;
import java.util.*;

/** @author scoperetail */
public class State {
  private String name;
  private String comment;
  private boolean isInitState = false;
  private boolean isEndState = false;
  private Map<String, Transition> transitions = new HashMap<>();
  private Map<String, Method> preConditions = new HashMap<>();
  private Map<String, Method> preActions = new HashMap<>();
  private Set<String> validEvents = new HashSet<>();
  // It is very important to use LinkedHashSet, as this will store
  // future events in the order they are applied
  private Set<String> futureEvents = new LinkedHashSet<>();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public boolean isInitState() {
    return isInitState;
  }

  public void setInitState(boolean isInitState) {
    this.isInitState = isInitState;
  }

  public boolean isEndState() {
    return isEndState;
  }

  public void setEndState(boolean isEndState) {
    this.isEndState = isEndState;
  }

  public Map<String, Transition> getTransitions() {
    return transitions;
  }

  public void setTransitions(Map<String, Transition> transitions) {
    this.transitions = transitions;
  }

  public void addTransition(String onEvent, State toState) {
    this.transitions.put(onEvent, new Transition(onEvent, toState.getName()));
  }

  public Map<String, Method> getPreConditions() {
    return preConditions;
  }

  public void setPreConditions(Map<String, Method> preConditions) {
    this.preConditions = preConditions;
  }

  public Map<String, Method> getPreActions() {
    return preActions;
  }

  public void setPreActions(Map<String, Method> preActions) {
    this.preActions = preActions;
  }

  public void addTransition(String onEvent, Transition transition) {
    this.transitions.put(onEvent, transition);
  }

  public void addPreCondition(String onEvent, Method method) {
    this.preConditions.put(onEvent, method);
  }

  public void addPreAction(String onEvent, Method method) {
    this.preActions.put(onEvent, method);
  }

  public Set<String> getValidEvents() {
    return validEvents;
  }

  public void setValidEvents(Set<String> validEvents) {
    this.validEvents = validEvents;
  }

  public Set<String> getFutureEvents() {
    return futureEvents;
  }

  public void setFutureEvents(Set<String> futureEvents) {
    this.futureEvents = futureEvents;
  }

  public String getToState(PendingEvent onEvent) {
    if (this.transitions.containsKey(onEvent.getEventName())) {
      return this.transitions.get(onEvent.getEventName()).getToState();
    }
    return null;
  }

  public Method getPreCondition(PendingEvent onEvent) {
    if (this.preConditions.containsKey(onEvent.getEventName())) {
      return this.preConditions.get(onEvent.getEventName());
    }
    return null;
  }

  public Method getPreAction(PendingEvent onEvent) {
    if (this.preActions.containsKey(onEvent.getEventName())) {
      return this.preActions.get(onEvent.getEventName());
    }
    return null;
  }

  public boolean isValidEvent(PendingEvent onEvent) {
    return this.getValidEvents().contains(onEvent.getEventName());
  }

  public boolean isFutureEvent(PendingEvent onEvent) {
    return this.getFutureEvents().contains(onEvent.getEventName());
  }
}

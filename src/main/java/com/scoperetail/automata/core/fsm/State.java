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

import com.scoperetail.automata.core.persistence.entity.PendingEvent;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;
import java.util.*;

@Getter
@Setter
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

  public void addTransition(String onEvent, State toState) {
    this.transitions.put(onEvent, new Transition(onEvent, toState.getName()));
  }

  public void addPreCondition(String onEvent, Method method) {
    this.preConditions.put(onEvent, method);
  }

  public void addPreAction(String onEvent, Method method) {
    this.preActions.put(onEvent, method);
  }

  public String getToState(PendingEvent onEvent) {
    String toState = null;
    if (this.transitions.containsKey(onEvent.getEventName())) {
      toState = this.transitions.get(onEvent.getEventName()).getToState();
    }
    return toState;
  }

  public Method getPreCondition(PendingEvent onEvent) {
    if (this.preConditions.containsKey(onEvent.getEventName())) {
      return this.preConditions.get(onEvent.getEventName());
    }
    return null;
  }

  public Method getPreAction(PendingEvent onEvent) {
    Method preAction = null;
    if (this.preActions.containsKey(onEvent.getEventName())) {
      preAction = this.preActions.get(onEvent.getEventName());
    }
    return preAction;
  }

  public boolean isValidEvent(PendingEvent onEvent) {
    return this.getValidEvents().contains(onEvent.getEventName());
  }

  public boolean isFutureEvent(PendingEvent onEvent) {
    return this.getFutureEvents().contains(onEvent.getEventName());
  }
}

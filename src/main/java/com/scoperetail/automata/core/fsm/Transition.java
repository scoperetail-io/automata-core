package com.scoperetail.automata.core.fsm;
/** @author scoperetail */
public class Transition {

  private String onEvent;
  private String toState;

  public Transition(String onEvent, String toState) {
    super();
    this.onEvent = onEvent;
    this.toState = toState;
  }

  public String getOnEvent() {
    return onEvent;
  }

  public void setOnEvent(String onEvent) {
    this.onEvent = onEvent;
  }

  public String getToState() {
    return toState;
  }

  public void setToState(String toState) {
    this.toState = toState;
  }
}

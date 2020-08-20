package com.scoperetail.automata.core.automata;
/**
 * 
 * @author scoperetail
 *
 */
public class Transition {
	
	public Transition(String onEvent, String toState) {
		super();
		this.onEvent = onEvent;
		this.toState = toState;
	}
	
	private String onEvent;
	
	private String toState;
	
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

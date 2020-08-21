package com.scoperetail.automata.core.fsm;

import com.scoperetail.automata.core.exception.StateAutomataException;
import com.scoperetail.automata.core.persistence.entity.PendingEvent;

@FunctionalInterface
public interface FiniteStateMachine {
    void onEvent(PendingEvent onEvent) throws StateAutomataException;
}

package com.scoperetail.automata.core.client.impl;

import com.scoperetail.automata.core.client.spi.Automata;
import com.scoperetail.automata.core.client.spi.AutomataEvent;
import com.scoperetail.automata.core.exception.StateAutomataException;
import com.scoperetail.automata.core.fsm.FiniteStateMachine;
import com.scoperetail.automata.core.helper.EventHelper;
import org.springframework.stereotype.Component;

@Component
public final class AutomataImpl implements Automata {
  private final FiniteStateMachine finiteStateMachine;
  private final EventHelper eventHelper;

  public AutomataImpl(final FiniteStateMachine finiteStateMachine, final EventHelper eventHelper) {
    this.finiteStateMachine = finiteStateMachine;
    this.eventHelper = eventHelper;
  }

  @Override
  public void processEvent(final AutomataEvent automataEvent) throws StateAutomataException {
    finiteStateMachine.onEvent(eventHelper.getAutomataEventForMessage(automataEvent));
  }
}

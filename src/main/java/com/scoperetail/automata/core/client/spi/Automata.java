package com.scoperetail.automata.core.client.spi;

import com.scoperetail.automata.core.exception.StateAutomataException;

@FunctionalInterface
public interface Automata {
  void processEvent(final AutomataEvent automataEvent) throws StateAutomataException;
}

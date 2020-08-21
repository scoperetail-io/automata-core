package com.scoperetail.automata.core.fixtures.automata;

import com.scoperetail.automata.core.annotations.Automata;
import com.scoperetail.automata.core.annotations.Event;
import com.scoperetail.automata.core.annotations.State;
import com.scoperetail.automata.core.annotations.Transition;
import com.scoperetail.automata.core.service.StateEntityService;
import org.springframework.stereotype.Component;

@Automata(
    endState = "GARBAGE",
    startState = "GARBAGE",
    // Using invalid start and end state should result in StateAutomataException
    transitions = {
      @Transition(from = "START", event = "E1", to = "SECOND"),
      @Transition(from = "SECOND", event = "E2", to = "THIRD"),
      @Transition(from = "THIRD", event = "E3", to = "FOURTH"),
      @Transition(from = "FOURTH", event = "E4", to = "FIFTH"),
      @Transition(from = "FIFTH", event = "E5", to = "END")
    },
    states = {
      @State(name = "START", comment = "Starting State"),
      @State(name = "SECOND", comment = "SECOND State"),
      @State(name = "THIRD", comment = "THIRD State"),
      @State(name = "FOURTH", comment = "FOURTH State"),
      @State(name = "FIFTH", comment = "FIFTH State"),
      @State(name = "END", comment = "End State"),
    },
    events = {
      @Event(name = "E1", comment = "START --E1---> SECOND"),
      @Event(name = "E2", comment = "SECOND --E2---> THIRD"),
      @Event(name = "E3", comment = "THIRD --E3---> FOURTH"),
      @Event(name = "E4", comment = "FOURTH --E4---> FIFTH"),
      @Event(name = "E5", comment = "FIFTH --E5---> END")
    },
    name = "StartOrEndMissingOrder",
    clazz = StartOrEndMissingOrder.class,
    service = StateEntityService.class)
@Component(value = "StartOrEndMissingOrder")
public class StartOrEndMissingOrder {}

package com.scoperetail.automata.core.fixtures.automata;

import com.scoperetail.automata.core.annotations.*;
import com.scoperetail.automata.core.fixtures.random.RandomWorkService;
import com.scoperetail.automata.core.model.StateEntity;
import com.scoperetail.automata.core.service.StateEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Automata(
    endState = "END",
    startState = "START",
    transitions = {
      @Transition(from = "START", event = "E1", to = "SECOND"),
      @Transition(from = "SECOND", event = "E2", to = "THIRD"),
      @Transition(from = "THIRD", event = "E3", to = "END")
    },
    states = {
      @State(name = "START", comment = "Starting State"),
      @State(name = "SECOND", comment = "SECOND State"),
      @State(name = "THIRD", comment = "THIRD State"),
      @State(name = "END", comment = "End State"),
    },
    events = {
      @Event(name = "E1", comment = "START --E1---> SECOND"),
      @Event(name = "E2", comment = "SECOND --E2---> THIRD"),
      @Event(name = "E3", comment = "THIRD --E3---> END")
    },
    name = "TestRushOrder",
    clazz = TestRushOrder.class,
    service = StateEntityService.class)
@Component(value = "TestRushOrder")
public class TestRushOrder {

  @Autowired
  RandomWorkService randomWorkService;

  @Precondition(transition = @Transition(from = "START", event = "E1", to = "SECOND"))
  public boolean P01(StateEntity entity, com.scoperetail.automata.core.model.Event e) {
    System.out.println("running P01 for: " + entity);
    System.out.println("******checking if its OK to transition on:******" + e);
    System.out.println("******checking if its OK to transition******");
    randomWorkService.checkSomething("something");
    return true;
  }

  @Precondition(transition = @Transition(from = "SECOND", event = "E2", to = "THIRD"))
  public boolean P12(StateEntity entity, com.scoperetail.automata.core.model.Event e) {
    System.out.println("running P12 for: " + entity);
    System.out.println("******checking if its OK to transition on:******" + e);
    return true;
  }

  @Preaction(transition = @Transition(from = "START", event = "E1", to = "SECOND"))
  public boolean A01(StateEntity entity, com.scoperetail.automata.core.model.Event e) {
    System.out.println("running A01 for: " + entity);
    System.out.println("******checking if its OK to transition on:******" + e);
    randomWorkService.doSomeWork();
    return true;
  }

  @Preaction(transition = @Transition(from = "SECOND", event = "E2", to = "THIRD"))
  public boolean A12(StateEntity entity, com.scoperetail.automata.core.model.Event e) {
    System.out.println("running A12 for: " + entity);
    System.out.println("******checking if its OK to transition on:******" + e);
    return true;
  }
}

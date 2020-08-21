package com.scoperetail.automata.core.fsm;

import com.scoperetail.automata.core.exception.StateAutomataException;
import com.scoperetail.automata.core.persistence.entity.PendingEvent;
import com.scoperetail.automata.core.persistence.entity.RejectedEvent;
import com.scoperetail.automata.core.persistence.entity.StateEntity;
import com.scoperetail.automata.core.service.EventService;
import com.scoperetail.automata.core.service.StateEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/** @author scoperetail */
@Component
@Slf4j
public class FSMHandler implements FiniteStateMachine {

  private final FSMCollection fsmCollection;
  private final StateEntityService orderService;
  private final EventService eventService;

  public FSMHandler(
      final FSMCollection fsmCollection,
      final StateEntityService orderService,
      final EventService eventService) {
    this.fsmCollection = fsmCollection;
    this.orderService = orderService;
    this.eventService = eventService;
  }

  @Override
  public void onEvent(PendingEvent onEvent) throws StateAutomataException {
    FSM fsm = null;
    if (onEvent.getAutomataName() != null) {
      if (fsmCollection.getFsmMapByName().containsKey(onEvent.getAutomataName())) {
        fsm = fsmCollection.getFSMByName(onEvent.getAutomataName());
      } else if ("".equals(onEvent.getAutomataName())) {
        // Type will be taken from Entity
      } else {
        throw new StateAutomataException(
            "Unsupported PendingEvent/Order Type: " + onEvent.getAutomataName());
      }
    }

    StateEntity s = orderService.findByStateEntityNum(onEvent.getKey());
    if (s != null && fsm == null) {
      fsm = fsmCollection.getFSMByName(s.getAutomataType());
    }
    if (fsm == null) {
      RejectedEvent rejectedEvent = RejectedEvent.of(onEvent);
      eventService.save(rejectedEvent);
      log.error(
          "First event should contain the order type to be mapped with automata: {}", onEvent);
      return;
    }
    if (s == null) {
      // this is a new entity, since we dont know the order type it needs to come from event
      s = new StateEntity();
      s.setLookupKey(onEvent.getLookupKey());
      s.setKey(onEvent.getKey());
      s.setAutomataType(onEvent.getAutomataName());
    }
    fsm.onEvent(s, onEvent);
  }
}

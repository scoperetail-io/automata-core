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

    StateEntity s = orderService.findByStateEntityNum(onEvent.getEntityId());
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
      s.setEntityId(onEvent.getEntityId());
      s.setAutomataType(onEvent.getAutomataName());
    }
    fsm.onEvent(s, onEvent);
  }
}

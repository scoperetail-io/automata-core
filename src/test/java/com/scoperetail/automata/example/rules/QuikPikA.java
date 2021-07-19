package com.scoperetail.automata.example.rules;

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

import com.scoperetail.automata.core.annotations.*;
import com.scoperetail.automata.core.persistence.entity.PendingEvent;
import com.scoperetail.automata.core.persistence.entity.StateEntity;
import com.scoperetail.automata.core.service.StateEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/** @author scoperetail */

/*
 *     NEW ----(CREATED)---->SCHEDULE
 *      |
 *      |
 *      |
 *      |
 *      |
 *   (CANCEL)---->CANCEL
 */

@Automata(
    endState = {"CANCEL", "END"},
    startState = "NEW",
    transitions = {
      @Transition(from = "NEW", event = "CREATED", to = "SCHEDULE"),
      @Transition(from = "NEW", event = "CANCELED", to = "CANCEL"),
      @Transition(from = "SCHEDULE", event = "RELEASED", to = "ROUTE"),
      @Transition(from = "ROUTE", event = "ROUTED", to = "PICKING"),
      @Transition(from = "SCHEDULE", event = "CANCELED", to = "CANCEL"),
      @Transition(from = "PICKING", event = "PICK_COMPLETE", to = "STAGE"),
      @Transition(from = "STAGE", event = "STAGE_COMPLETE", to = "DISPENSE"),
      @Transition(from = "DISPENSE", event = "DISPENSE_COMPLETE", to = "END"),
      @Transition(from = "DISPENSE", event = "REJECTED", to = "END")
    },
    states = {
      @State(name = "NEW", comment = "Starting State"),
      @State(name = "SCHEDULE"),
      @State(name = "ROUTE"),
      @State(name = "PICKING"),
      @State(name = "STAGE"),
      @State(name = "DISPENSE"),
      @State(name = "CANCEL"),
      @State(name = "END")
    },
    events = {
      @Event(name = "CREATED"),
      @Event(name = "CANCELED"),
      @Event(name = "RELEASED"),
      @Event(name = "ROUTED"),
      @Event(name = "PICK_COMPLETE"),
      @Event(name = "STAGE_COMPLETE"),
      @Event(name = "DISPENSE_COMPLETE"),
      @Event(name = "REJECTED", comment = "REJECTED by customer at the time of dispense")
    },
    name = "QUIKPIK_A",
    clazz = QuikPikA.class,
    service = StateEntityService.class)
// This is sample implementation
@SuppressWarnings({"squid:S3824", "squid:S1192"})
@Component(value = "QUIKPIK_A")
@Slf4j
public class QuikPikA {

  @Preaction(transition = @Transition(from = "NEW", event = "CREATED", to = "SCHEDULE"))
  public boolean aNewToSchedule(StateEntity entity, PendingEvent e) {
    log.info("entity:{}, pendingEvent:{}", entity, e);
    // Notify if needed using a service
    return true;
  }

  @Preaction(transition = @Transition(from = "NEW", event = "CANCELED", to = "CANCEL"))
  public boolean aNewToCancel(StateEntity entity, PendingEvent e) {
    log.info("entity:{}, pendingEvent:{}", entity, e);
    // Notify if needed using a service
    return true;
  }

  @Preaction(transition = @Transition(from = "SCHEDULE", event = "RELEASED", to = "ROUTE"))
  public boolean aScheduleToRoute(StateEntity entity, PendingEvent e) {
    log.info("entity:{}, pendingEvent:{}", entity, e);
    // Notify if needed using a service
    return true;
  }

  @Preaction(transition = @Transition(from = "ROUTE", event = "ROUTED", to = "PICKING"))
  public boolean aRouteToPicking(StateEntity entity, PendingEvent e) {
    log.info("entity:{}, pendingEvent:{}", entity, e);
    // Notify if needed using a service
    return true;
  }

  @Preaction(transition = @Transition(from = "SCHEDULE", event = "CANCELED", to = "CANCEL"))
  public boolean aScheduleToCancel(StateEntity entity, PendingEvent e) {
    log.info("entity:{}, pendingEvent:{}", entity, e);
    // Notify if needed using a service
    return true;
  }

  @Preaction(transition = @Transition(from = "PICKING", event = "PICK_COMPLETE", to = "STAGE"))
  public boolean aPickingToStage(StateEntity entity, PendingEvent e) {
    log.info("entity:{}, pendingEvent:{}", entity, e);
    // Notify if needed using a service
    return true;
  }

  @Preaction(transition = @Transition(from = "STAGE", event = "STAGE_COMPLETE", to = "DISPENSE"))
  public boolean aStageToDispense(StateEntity entity, PendingEvent e) {
    log.info("entity:{}, pendingEvent:{}", entity, e);
    // Notify if needed using a service
    return true;
  }

  @Preaction(transition = @Transition(from = "DISPENSE", event = "DISPENSE_COMPLETE", to = "END"))
  public boolean aDispenseToEndOnComplete(StateEntity entity, PendingEvent e) {
    log.info("entity:{}, pendingEvent:{}", entity, e);
    // Notify if needed using a service
    return true;
  }

  @Preaction(transition = @Transition(from = "DISPENSE", event = "REJECTED", to = "END"))
  public boolean aDispenseToEndOnReject(StateEntity entity, PendingEvent e) {
    log.info("entity:{}, pendingEvent:{}", entity, e);
    // Notify if needed using a service
    return true;
  }
}

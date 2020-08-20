package com.scoperetail.automata.core.config;

import com.scoperetail.automata.core.annotations.*;
import com.scoperetail.automata.core.model.StateEntity;
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
      @Transition(from = "NEW", event = "CANCEL", to = "CANCEL"),
      @Transition(from = "SCHEDULE", event = "CANCEL", to = "CANCEL")
    },
    states = {
      @State(name = "NEW", comment = "Starting State"),
      @State(name = "SCHEDULE", comment = "After getting CREATED message from COP"),
      @State(name = "CANCEL", comment = "After getting CANCEL message"),
      @State(name = "END", comment = "After getting RELEASED message from Releaser"),
    },
    events = {
      @Event(name = "CREATED", comment = "NEW --CREATED---> SCHEDULE"),
      @Event(name = "CANCEL", comment = "NEW --CANCEL---> CANCEL"),
    },
    name = "QUIKPIK_A",
    clazz = QuikPikA.class,
    service = StateEntityService.class)
// This is sample implementation
@SuppressWarnings({"squid:S3824", "squid:S1192"})
@Component(value = "QUIKPIK_A")
@Slf4j
public class QuikPikA {

  @Precondition(transition = @Transition(from = "NEW", event = "CREATED", to = "SCHEDULE"))
  public boolean pNewToSchedule(StateEntity entity, com.scoperetail.automata.core.model.Event e) {
    log.info("running pNewToSchedule for: {}", entity);
    log.info("******checking if its OK to transition on:****** {}", e);
    return true;
  }

  @Preaction(transition = @Transition(from = "NEW", event = "CREATED", to = "SCHEDULE"))
  public boolean aNewToSchedule(StateEntity entity, com.scoperetail.automata.core.model.Event e) {
    log.info("running aNewToSchedule for: {}", entity);
    log.info("******checking if its OK to transition on:****** {}", e);
    // Notify schedule using a contrive service
    return true;
  }

  @Precondition(transition = @Transition(from = "NEW", event = "CANCEL", to = "CANCEL"))
  public boolean pNewToCancel(StateEntity entity, com.scoperetail.automata.core.model.Event e) {
    log.info("running pNewToCancel for: {}", entity);
    log.info("******checking if its OK to transition on:****** {}", e);
    return true;
  }

  @Preaction(transition = @Transition(from = "NEW", event = "CANCEL", to = "CANCEL"))
  public boolean aNewToCancel(StateEntity entity, com.scoperetail.automata.core.model.Event e) {
    log.info("running aNewToCancel for: {}", entity);
    log.info("******checking if its OK to transition on:****** {}", e);
    // Notify schedule using a contrive service
    return true;
  }

  @Precondition(transition = @Transition(from = "SCHEDULE", event = "CANCEL", to = "CANCEL"))
  public boolean pScheduleToCancel(
      StateEntity entity, com.scoperetail.automata.core.model.Event e) {
    log.info("running pScheduleToCancel for: {}", entity);
    log.info("******checking if its OK to transition on:****** {}", e);
    return true;
  }

  @Preaction(transition = @Transition(from = "SCHEDULE", event = "CANCEL", to = "CANCEL"))
  public boolean aScheduleToCancel(
      StateEntity entity, com.scoperetail.automata.core.model.Event e) {
    log.info("running aScheduleToCancel for: {}", entity);
    log.info("******checking if its OK to transition on:****** {}", e);
    // Notify schedule using a contrive service
    return true;
  }
}

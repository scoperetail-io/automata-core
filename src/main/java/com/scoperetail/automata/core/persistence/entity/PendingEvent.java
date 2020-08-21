package com.scoperetail.automata.core.persistence.entity;

import com.scoperetail.automata.core.spi.AutomataEvent;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "PendingEvent")
@Table(name = "pending_event")
@NoArgsConstructor
public class PendingEvent extends BaseEvent {
  public PendingEvent(final AutomataEvent automataEvent, final String automataName) {
    super(automataEvent, automataName);
  }

  public static PendingEvent of(final AutomataEvent automataEvent, final String automataName) {
    return new PendingEvent(automataEvent, automataName);
  }
}

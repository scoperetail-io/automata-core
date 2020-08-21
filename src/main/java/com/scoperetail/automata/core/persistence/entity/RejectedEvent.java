package com.scoperetail.automata.core.persistence.entity;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "RejectedEvent")
@Table(name = "rejected_event")
@NoArgsConstructor
public class RejectedEvent extends BaseEvent {

  public RejectedEvent(final PendingEvent event) {
    super(event);
  }

  public static RejectedEvent of(final PendingEvent event) {
    return new RejectedEvent(event);
  }
}

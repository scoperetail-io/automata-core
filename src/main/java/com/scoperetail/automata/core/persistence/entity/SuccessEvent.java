package com.scoperetail.automata.core.persistence.entity;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

/** @author scoperetail */
@Entity(name = "SuccessEvent")
@Table(name = "success_event")
@NoArgsConstructor
public class SuccessEvent extends BaseEvent {
  public SuccessEvent(final PendingEvent event) {
    super(event);
  }

  public static SuccessEvent of(final PendingEvent event) {
    return new SuccessEvent(event);
  }
}

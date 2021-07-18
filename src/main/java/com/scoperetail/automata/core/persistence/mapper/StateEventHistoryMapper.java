package com.scoperetail.automata.core.persistence.mapper;

import com.scoperetail.automata.core.persistence.entity.PendingEvent;
import com.scoperetail.automata.core.persistence.entity.RejectedEvent;
import com.scoperetail.automata.core.persistence.entity.StateEntity;
import com.scoperetail.automata.core.persistence.entity.SuccessEvent;

public interface StateEventHistoryMapper {

  StateEntity getStateEntity();

  RejectedEvent getRejectedEvent();

  SuccessEvent getSuccessEvent();

  PendingEvent getPendingEvent();
}

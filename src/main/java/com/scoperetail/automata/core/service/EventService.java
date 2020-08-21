package com.scoperetail.automata.core.service;

import com.scoperetail.automata.core.persistence.entity.PendingEvent;
import com.scoperetail.automata.core.persistence.entity.RejectedEvent;
import com.scoperetail.automata.core.persistence.entity.SuccessEvent;

import java.util.List;

/** @author scoperetail */
public interface EventService {

  PendingEvent find(Long id);

  void save(PendingEvent event);

  void incrementRetry(PendingEvent event);

  void save(RejectedEvent rejectedEvent);

  void save(SuccessEvent successEvent);

  List<PendingEvent> findAll();

  List<PendingEvent> findByKeySortByCreateTS(final String key);
}

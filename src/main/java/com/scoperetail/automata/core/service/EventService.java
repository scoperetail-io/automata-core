package com.scoperetail.automata.core.service;

import com.scoperetail.automata.core.persistence.entity.Event;
import com.scoperetail.automata.core.persistence.entity.RejectedEvent;
import com.scoperetail.automata.core.persistence.entity.SuccessEvent;

import java.util.List;

/** @author scoperetail */
public interface EventService {

  Event find(Long id);

  void save(Event event);

  void incrementRetry(Event event);

  void save(RejectedEvent rejectedEvent);

  void save(SuccessEvent successEvent);

  List<Event> findAll();

  List<Event> findByKeySortByCreateTS(final String key);
}

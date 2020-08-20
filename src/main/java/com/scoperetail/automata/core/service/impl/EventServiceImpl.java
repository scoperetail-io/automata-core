package com.scoperetail.automata.core.service.impl;

import com.scoperetail.automata.core.model.Event;
import com.scoperetail.automata.core.model.RejectedEvent;
import com.scoperetail.automata.core.model.SuccessEvent;
import com.scoperetail.automata.core.repo.EventRepository;
import com.scoperetail.automata.core.repo.RejectedEventRepository;
import com.scoperetail.automata.core.repo.SuccessEventRepository;
import com.scoperetail.automata.core.service.EventService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/** @author scoperetail */
@Service("eventService")
@Transactional
public class EventServiceImpl implements EventService {

  @Resource EventRepository eventRepository;

  @Resource RejectedEventRepository rejectedEventRepository;

  @Resource SuccessEventRepository successEventRepository;

  @PersistenceContext EntityManager entityManager;

  @Override
  public Event find(Long id) {
    Optional<Event> event = eventRepository.findById(id);
    if (event.isPresent()) {
      return event.get();
    } else {
      return null;
    }
  }

  @Override
  public void save(Event event) {
    eventRepository.save(event);
    eventRepository.flush();
  }

  @Override
  public void incrementRetry(Event event) {
    Event eventManaged = find(event.getId());
    if (eventManaged != null) {
      eventManaged.setRetryCount(eventManaged.getRetryCount() + 1);
      eventRepository.flush();
    }
  }

  @Override
  public List<Event> findAll() {
    return eventRepository.findAll();
  }

  @Override
  public List<Event> findByKeySortByCreateTS(final String key) {
    return eventRepository.findByKeySortByCreateTS(key);
  }

  @Override
  public void save(RejectedEvent rejectedEvent) {
    rejectedEventRepository.save(rejectedEvent);
    rejectedEventRepository.flush();
  }

  @Override
  public void save(SuccessEvent successEvent) {
    successEventRepository.save(successEvent);
    successEventRepository.flush();
  }
}

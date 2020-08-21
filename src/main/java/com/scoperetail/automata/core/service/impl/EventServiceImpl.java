package com.scoperetail.automata.core.service.impl;

import com.scoperetail.automata.core.persistence.entity.PendingEvent;
import com.scoperetail.automata.core.persistence.entity.RejectedEvent;
import com.scoperetail.automata.core.persistence.entity.SuccessEvent;
import com.scoperetail.automata.core.persistence.repository.EventRepository;
import com.scoperetail.automata.core.persistence.repository.RejectedEventRepository;
import com.scoperetail.automata.core.persistence.repository.SuccessEventRepository;
import com.scoperetail.automata.core.service.EventService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/** @author scoperetail */
@Service("eventService")
@Transactional
public class EventServiceImpl implements EventService {

  @Resource private EventRepository eventRepository;

  @Resource private RejectedEventRepository rejectedEventRepository;

  @Resource private SuccessEventRepository successEventRepository;

  @Override
  public PendingEvent find(Long id) {
    Optional<PendingEvent> event = eventRepository.findById(id);
    if (event.isPresent()) {
      return event.get();
    } else {
      return null;
    }
  }

  @Override
  public void save(PendingEvent event) {
    eventRepository.save(event);
    eventRepository.flush();
  }

  @Override
  public void incrementRetry(PendingEvent event) {
    PendingEvent eventManaged = find(event.getId());
    if (eventManaged != null) {
      eventManaged.setRetryCount(eventManaged.getRetryCount() + 1);
      eventRepository.flush();
    }
  }

  @Override
  public List<PendingEvent> findAll() {
    return eventRepository.findAll();
  }

  @Override
  public List<PendingEvent> findByKeySortByCreateTS(final String key) {
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

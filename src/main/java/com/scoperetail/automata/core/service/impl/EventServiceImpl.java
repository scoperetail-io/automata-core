package com.scoperetail.automata.core.service.impl;

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

import com.scoperetail.automata.core.persistence.entity.PendingEvent;
import com.scoperetail.automata.core.persistence.entity.RejectedEvent;
import com.scoperetail.automata.core.persistence.entity.SuccessEvent;
import com.scoperetail.automata.core.persistence.repository.PendingEventRepository;
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

  @Resource private PendingEventRepository pendingEventRepository;

  @Resource private RejectedEventRepository rejectedEventRepository;

  @Resource private SuccessEventRepository successEventRepository;

  @Override
  public PendingEvent find(Long id) {
    Optional<PendingEvent> event = pendingEventRepository.findById(id);
    if (event.isPresent()) {
      return event.get();
    } else {
      return null;
    }
  }

  @Override
  public void save(PendingEvent event) {
    pendingEventRepository.save(event);
    pendingEventRepository.flush();
  }

  @Override
  public void incrementRetry(PendingEvent event) {
    PendingEvent eventManaged = find(event.getId());
    if (eventManaged != null) {
      eventManaged.setRetryCount(eventManaged.getRetryCount() + 1);
      pendingEventRepository.flush();
    }
  }

  @Override
  public List<PendingEvent> findAll() {
    return pendingEventRepository.findAll();
  }

  @Override
  public List<PendingEvent> findByKeySortByCreateTS(final String key) {
    return pendingEventRepository.findByKeySortByCreateTS(key);
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

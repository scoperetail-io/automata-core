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

import com.scoperetail.automata.core.persistence.entity.StateEntity;
import com.scoperetail.automata.core.persistence.repository.StateEntityRepository;
import com.scoperetail.automata.core.service.StateEntityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/** @author scoperetail */
@Service("orderService")
@Transactional
public class StateEntityServiceImpl implements StateEntityService {

  @Resource
  StateEntityRepository orderRepo;

  @PersistenceContext EntityManager entityManager;

  @Override
  public StateEntity find(Long id) {
    Optional<StateEntity> order = orderRepo.findById(id);
    if (order.isPresent()) {
      return order.get();
    } else {
      return null;
    }
  }

  public List<StateEntity> findAll() {
    return orderRepo.findAll();
  }

  @Override
  public void save(StateEntity order) {
    this.orderRepo.save(order);
    this.entityManager.flush();
  }

  public void updateState(long id, String stateName) {
    StateEntity order = find(id);
    order.setStateName(stateName);
    this.entityManager.flush();
  }

  @Override
  public StateEntity findEntity(Long id) {
    return this.find(id);
  }

  @Override
  public void saveEntity(StateEntity entity) {
    this.save(entity);
  }

  @Override
  public StateEntity findByStateEntityNum(final String key) {
    return orderRepo.findByEntityId(key);
  }
}

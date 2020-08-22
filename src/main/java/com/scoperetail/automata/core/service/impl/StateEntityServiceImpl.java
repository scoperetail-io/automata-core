package com.scoperetail.automata.core.service.impl;

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
    return orderRepo.findByKey(key);
  }
}

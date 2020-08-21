package com.scoperetail.automata.core.service;

import com.scoperetail.automata.core.fsm.FSMService;
import com.scoperetail.automata.core.persistence.entity.StateEntity;

import java.util.List;

/** @author scoperetail */
public interface StateEntityService extends FSMService {

  StateEntity find(Long id);

  void save(StateEntity order);

  List<StateEntity> findAll();

  void updateState(long id, String stateName);

  StateEntity findByStateEntityNum(String orderNumber);
}

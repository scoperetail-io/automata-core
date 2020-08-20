package com.scoperetail.automata.core.service;

import com.scoperetail.automata.core.automata.FSMService;
import com.scoperetail.automata.core.model.StateEntity;

import java.util.List;

/** @author scoperetail */
public interface StateEntityService extends FSMService {

  StateEntity find(Long id);

  void save(StateEntity order);

  public List<StateEntity> findAll();

  public void updateState(long id, String stateName);

  public StateEntity findByStateEntityNum(String orderNumber);
}

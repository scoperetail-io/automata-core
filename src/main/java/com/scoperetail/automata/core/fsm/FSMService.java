package com.scoperetail.automata.core.fsm;

import com.scoperetail.automata.core.model.StateEntity;

/** @author scoperetail */
public interface FSMService {

  StateEntity findEntity(Long id);

  void saveEntity(StateEntity entity);

  void updateState(long id, String stateName);
}

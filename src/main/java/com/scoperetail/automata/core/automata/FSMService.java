package com.scoperetail.automata.core.automata;

import com.scoperetail.automata.core.model.StateEntity;

/** @author scoperetail */
public interface FSMService {

  StateEntity findEntity(Long id);

  void saveEntity(StateEntity entity);

  public void updateState(long id, String stateName);
}

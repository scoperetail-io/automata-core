package com.scoperetail.automata.core.automata;

import com.scoperetail.automata.core.automata.config.QuikPikA;
import com.scoperetail.automata.core.automata.exception.DisconnectedGraphException;
import com.scoperetail.automata.core.automata.exception.StateAutomataException;
import com.scoperetail.automata.core.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/** @author scoperetail */
@Component
public class FSMCollection {

  @Autowired private ApplicationContext applicationContext;

  @Autowired private EventService eventService;

  Map<String, FSM> fsmMapByName = new HashMap<>();

  public void addFSMByName(String fsmName, FSM fsm) {
    fsmMapByName.put(fsmName, fsm);
  }

  public FSM getFSMByName(String fsmName) {
    return fsmMapByName.get(fsmName);
  }

  public Map<String, FSM> getFsmMapByName() {
    return fsmMapByName;
  }

  public void setFsmMapByName(Map<String, FSM> fsmMapByName) {
    this.fsmMapByName = fsmMapByName;
  }

  @PostConstruct
  public void init() throws DisconnectedGraphException, StateAutomataException {
    // TODO: Find all automata classes using reflection
//    FSMHarness.harnessFSM(this, applicationContext, eventService, ABSOrder.class);
//    FSMHarness.harnessFSM(this, applicationContext, eventService, RushOrder.class);
//    FSMHarness.harnessFSM(this, applicationContext, eventService, CatAOrder.class);
//    FSMHarness.harnessFSM(this, applicationContext, eventService, CatBOrder.class);
    FSMHarness.harnessFSM(this, applicationContext, eventService, QuikPikA.class);
  }
}

package com.scoperetail.automata.core.fsm;

import com.scoperetail.automata.core.annotations.Automata;
import com.scoperetail.automata.core.exception.DisconnectedGraphException;
import com.scoperetail.automata.core.exception.StateAutomataException;
import com.scoperetail.automata.core.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/** @author scoperetail */
@Component
@Slf4j
public class FSMCollection {

  Map<String, FSM> fsmMapByName = new HashMap<>();
  @Autowired private ApplicationContext applicationContext;
  @Autowired private EventService eventService;

  public void addFSMByName(String fsmName, FSM fsm) {
    fsmMapByName.put(fsmName, fsm);
  }

  public FSM getFSMByName(String fsmName) {
    return fsmMapByName.get(fsmName);
  }

  public Map<String, FSM> getFsmMapByName() {
    return fsmMapByName;
  }

  @PostConstruct
  public void init() throws DisconnectedGraphException, StateAutomataException {
    final Set<Class<?>> classes = getAutomatas();
    log.info("automatas found: {}", classes);
    for (Class clazz : classes) {
      FSMHarness.harnessFSM(this, applicationContext, eventService, clazz);
    }
  }

  private Set<Class<?>> getAutomatas() {
    Reflections reflections =
        new Reflections(
            "com.scoperetail.automata.example",
            new TypeAnnotationsScanner(),
            new SubTypesScanner());
    return reflections.getTypesAnnotatedWith(Automata.class);
  }
}

package com.scoperetail.automata.core.fsm;

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

import com.scoperetail.automata.core.annotations.Automata;
import com.scoperetail.automata.core.exception.DisconnectedGraphException;
import com.scoperetail.automata.core.exception.StateAutomataException;
import com.scoperetail.automata.core.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

  @Value("${automata.base.package}")
  private String basePackage;

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
        new Reflections(basePackage, new TypeAnnotationsScanner(), new SubTypesScanner());
    return reflections.getTypesAnnotatedWith(Automata.class);
  }
}

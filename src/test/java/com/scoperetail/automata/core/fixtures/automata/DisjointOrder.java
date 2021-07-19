package com.scoperetail.automata.core.fixtures.automata;

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
import com.scoperetail.automata.core.annotations.Event;
import com.scoperetail.automata.core.annotations.State;
import com.scoperetail.automata.core.annotations.Transition;
import com.scoperetail.automata.core.service.StateEntityService;
import org.springframework.stereotype.Component;

@Automata(
    endState = "END",
    startState = "START",
    transitions = {
      @Transition(from = "START", event = "E1", to = "SECOND"),
      @Transition(from = "SECOND", event = "E2", to = "THIRD"),

      // This makes the graph disjoint
      // @Transition(from="THIRD", event="E3", to="FOURTH"),

      @Transition(from = "FOURTH", event = "E4", to = "FIFTH"),
      @Transition(from = "FIFTH", event = "E5", to = "END")
    },
    states = {
      @State(name = "START", comment = "Starting State"),
      @State(name = "SECOND", comment = "SECOND State"),
      @State(name = "THIRD", comment = "THIRD State"),
      @State(name = "FOURTH", comment = "FOURTH State"),
      @State(name = "FIFTH", comment = "FIFTH State"),
      @State(name = "END", comment = "End State"),
    },
    events = {
      @Event(name = "E1", comment = "START --E1---> SECOND"),
      @Event(name = "E2", comment = "SECOND --E2---> THIRD"),
      @Event(name = "E3", comment = "THIRD --E3---> FOURTH"),
      @Event(name = "E4", comment = "FOURTH --E4---> FIFTH"),
      @Event(name = "E5", comment = "FIFTH --E5---> END")
    },
    name = "DisjointOrder",
    clazz = DisjointOrder.class,
    service = StateEntityService.class)
@Component(value = "DisjointOrder")
public class DisjointOrder {}

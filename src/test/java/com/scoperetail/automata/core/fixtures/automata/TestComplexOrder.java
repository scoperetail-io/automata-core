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

/*
 *
 *
 *     S1 ----e1---->S2-----e2------>S3
 *     |            |               ^
 *     |e3        e4|               |
 *     |            |               |e7
 *     v            v               |
 *     S4---e5---->S5-------e6---->S6
 *
 * Corresponding adjacency matrix
 * {S3=[], S4=[S5], S5=[S6], S6=[S3], S1=[S2, S4], S2=[S3, S5]}
 *
 */
@Automata(
    endState = "S3",
    startState = "S1",
    transitions = {
      @Transition(from = "S1", event = "E1", to = "S2"),
      @Transition(from = "S2", event = "E2", to = "S3"),
      @Transition(from = "S1", event = "E3", to = "S4"),
      @Transition(from = "S4", event = "E5", to = "S5"),
      @Transition(from = "S5", event = "E6", to = "S6"),
      @Transition(from = "S2", event = "E4", to = "S5"),
      @Transition(from = "S6", event = "E7", to = "S3")
    },
    states = {
      @State(name = "S1", comment = ""),
      @State(name = "S2", comment = ""),
      @State(name = "S3", comment = ""),
      @State(name = "S4", comment = ""),
      @State(name = "S5", comment = ""),
      @State(name = "S6", comment = "")
    },
    events = {
      @Event(name = "E1", comment = ""),
      @Event(name = "E2", comment = ""),
      @Event(name = "E3", comment = ""),
      @Event(name = "E4", comment = ""),
      @Event(name = "E5", comment = ""),
      @Event(name = "E6", comment = ""),
      @Event(name = "E7", comment = "")
    },
    name = "TestComplexOrder",
    clazz = TestComplexOrder.class,
    service = StateEntityService.class)
@Component(value = "TestComplexOrder")
public class TestComplexOrder {}

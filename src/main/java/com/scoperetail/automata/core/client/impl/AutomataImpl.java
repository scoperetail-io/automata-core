package com.scoperetail.automata.core.client.impl;

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

import com.scoperetail.automata.core.client.spi.Automata;
import com.scoperetail.automata.core.client.spi.AutomataEvent;
import com.scoperetail.automata.core.exception.StateAutomataException;
import com.scoperetail.automata.core.fsm.FiniteStateMachine;
import com.scoperetail.automata.core.helper.EventHelper;
import org.springframework.stereotype.Component;

@Component
public final class AutomataImpl implements Automata {
  private final FiniteStateMachine finiteStateMachine;
  private final EventHelper eventHelper;

  public AutomataImpl(final FiniteStateMachine finiteStateMachine, final EventHelper eventHelper) {
    this.finiteStateMachine = finiteStateMachine;
    this.eventHelper = eventHelper;
  }

  @Override
  public void processEvent(final AutomataEvent automataEvent) throws StateAutomataException {
    finiteStateMachine.onEvent(eventHelper.getAutomataEventForMessage(automataEvent));
  }
}

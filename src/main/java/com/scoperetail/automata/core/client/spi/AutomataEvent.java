package com.scoperetail.automata.core.client.spi;

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

import java.util.List;

/**
 * Automata event interface.
 *
 * <p>Client event must implement this interface and provide an adapter to convert from specific
 * business entity event to Automata event
 */
public interface AutomataEvent {

  /**
   * Lookup key finding the automata rules in the correct order for supporting wild card lookups
   *
   * @return list of Strings
   */
  List<String> lookupKeys();

  /**
   * Primary Lookup key for finding the automata rules without any wild cards
   *
   * @return list of Strings
   */
  String primaryLookupKey();

  /**
   * Name of the event defined in state chart
   *
   * @return event name
   */
  String getEventName();

  /**
   * Payload that needs to be stored along with event
   *
   * @return payload
   */
  String getPayload();

  /**
   * Id of business entity. For ex: order number
   *
   * @return ID
   */
  String getId();
}

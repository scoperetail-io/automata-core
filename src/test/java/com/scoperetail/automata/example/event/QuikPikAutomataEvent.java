package com.scoperetail.automata.example.event;

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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scoperetail.automata.core.client.spi.AutomataEvent;
import lombok.*;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class QuikPikAutomataEvent implements AutomataEvent {

  private static final String DELIMITER = "_";

  @JsonProperty("eventName")
  private String eventName;

  @JsonProperty("payload")
  private String payload;

  @JsonProperty("orderId")
  @EqualsAndHashCode.Include
  private String Id;

  @JsonProperty("orderType")
  private String orderType;

  @JsonProperty("deliveryMethod")
  private String deliveryMethod;

  @JsonProperty("fulfilBy")
  private String fulfilBy;

  @JsonProperty("tenantId")
  private String tenantId;

  @JsonProperty("fulfilLocation")
  private String fulfilLocation;

  @Override
  public List<String> lookupKeys() {
    StringBuilder builder = new StringBuilder(100);
    String partialKey =
        builder
            .append(orderType)
            .append(DELIMITER)
            .append(deliveryMethod)
            .append(DELIMITER)
            .append(fulfilBy)
            .append(DELIMITER)
            .append(tenantId)
            .toString();
    // fulfilLocation can be "ALL" or what came in the message.
    // So build the lookup key with specific fulfilLocation and with "ALL"
    return Arrays.asList(partialKey + DELIMITER + fulfilLocation, partialKey + DELIMITER + "ALL");
  }

  @Override
  public String primaryLookupKey() {
    StringBuilder builder = new StringBuilder(100);
    return builder
        .append(orderType)
        .append(DELIMITER)
        .append(deliveryMethod)
        .append(DELIMITER)
        .append(fulfilBy)
        .append(DELIMITER)
        .append(tenantId)
        .append(DELIMITER)
        .append(fulfilLocation)
        .toString();
  }
}

package com.scoperetail.automata.example.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scoperetail.automata.core.spi.AutomataEvent;
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

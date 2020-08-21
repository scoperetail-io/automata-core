package com.scoperetail.automata.core.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service("automataNameResolver")
@ConfigurationProperties(prefix = "automata.rule")
@Setter
@Getter
@Slf4j
/** Check the example under tests */
public class AutomataNameResolver {

  /** Example:: automata.rule.key.REGULAR_PICKUP_STORE_TENANT1_ALL */
  private Map<String, String> key;

  public String getAutomataName(final List<String> lookupKeys) {
    Objects.requireNonNull(lookupKeys);
    String automataName = null;
    for (String lookupKey : lookupKeys) {
      automataName = key.get(lookupKey);
      if (Objects.nonNull(automataName)) {
        break;
      }
    }
    return automataName;
  }
}

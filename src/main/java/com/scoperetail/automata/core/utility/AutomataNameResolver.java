package com.scoperetail.automata.core.utility;

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
public class AutomataNameResolver {

  private Map<String, String> key;


  @Deprecated
  public String getAutomataName(String supplierType, String customerType, String orderType) {
    String key = String.format("%s|%s|%s", supplierType, customerType, orderType);
    /*if (automatas.containsKey(key)) {
      return automatas.get(key);
    } else {
      return "";
    }*/
    return "";
  }

  public String getAutomataName(final List<String> lookupKeys) {
    Objects.requireNonNull(lookupKeys);
    String automataName = null;
    for (String lookupKey : lookupKeys) {
      automataName = key.get(lookupKey);
      if (Objects.isNull(automataName)) {
        continue;
      } else {
        break;
      }
    }
    return automataName;
  }
}

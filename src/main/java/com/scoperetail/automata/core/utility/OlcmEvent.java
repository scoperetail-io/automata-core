package com.scoperetail.automata.core.utility;

import java.util.List;

public interface OlcmEvent {
  String DELIMITER = "_";

  List<String> lookupKeys();

  String getLookupKey();

  String getEventName();

  String getPayload();

  String getKey();

  String getAttr1();

  String getAttr2();

  String getAttr3();

  String getAttr4();

  String getAttr5();
}

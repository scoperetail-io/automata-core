package com.scoperetail.automata.core.spi;

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

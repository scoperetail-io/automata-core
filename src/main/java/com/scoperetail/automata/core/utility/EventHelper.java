package com.scoperetail.automata.core.utility;

import com.scoperetail.automata.core.model.Event;
import com.scoperetail.automata.core.spi.AutomataEvent;
import org.springframework.stereotype.Service;

@Service
public class EventHelper {

  private final AutomataNameResolver automataNameResolver;

  public EventHelper(final AutomataNameResolver automataNameResolver) {
    this.automataNameResolver = automataNameResolver;
  }

  public Event getAutomataEventForMessage(final AutomataEvent event) {
    final String automataName = automataNameResolver.getAutomataName(event.lookupKeys());
    Event e = Event.of(event, automataName);
    return e;
  }
}

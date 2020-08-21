package com.scoperetail.automata.core.helper;

import com.scoperetail.automata.core.config.AutomataNameResolver;
import com.scoperetail.automata.core.persistence.entity.PendingEvent;
import com.scoperetail.automata.core.spi.AutomataEvent;
import org.springframework.stereotype.Service;

@Service
public class EventHelper {

  private final AutomataNameResolver automataNameResolver;

  public EventHelper(final AutomataNameResolver automataNameResolver) {
    this.automataNameResolver = automataNameResolver;
  }

  public PendingEvent getAutomataEventForMessage(final AutomataEvent event) {
    final String automataName = automataNameResolver.getAutomataName(event.lookupKeys());
    PendingEvent e =  PendingEvent.of(event, automataName);
    return e;
  }
}

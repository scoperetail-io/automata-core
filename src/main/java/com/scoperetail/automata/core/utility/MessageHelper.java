package com.scoperetail.automata.core.utility;

import com.scoperetail.automata.core.model.Event;
import org.springframework.stereotype.Service;

@Service("messageHelper")
public class MessageHelper {

  private final AutomataNameResolver automataNameResolver;

  public MessageHelper(final AutomataNameResolver automataNameResolver) {
    this.automataNameResolver = automataNameResolver;
  }

  public Event getAutomataEventForMessage(final OlcmEvent olcmEvent) {
    final String automataName = automataNameResolver.getAutomataName(olcmEvent.lookupKeys());
    Event e = Event.of(olcmEvent, automataName);
    return e;
  }
}

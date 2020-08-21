package com.scoperetail.automata.example.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.scoperetail.automata.core.client.spi.AutomataEvent;
import com.scoperetail.automata.example.event.QuikPikAutomataEvent;
import com.scoperetail.commons.json.util.JsonUtils;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AutomataEventFactory {
    public static AutomataEvent from(final String json) throws IOException {
        AutomataEvent automataEvent =
                JsonUtils.unmarshal(
                        Optional.ofNullable(json), Optional.of(new TypeReference<QuikPikAutomataEvent>() {
                        }));
        assertNotNull(automataEvent);
        return automataEvent;
    }
}

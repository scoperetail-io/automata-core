package com.scoperetail.automata.example.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.scoperetail.automata.core.AutomataCoreApplication;
import com.scoperetail.automata.core.exception.StateAutomataException;
import com.scoperetail.automata.core.fsm.FSMHandler;
import com.scoperetail.automata.core.persistence.entity.PendingEvent;
import com.scoperetail.automata.core.spi.AutomataEvent;
import com.scoperetail.automata.core.helper.EventHelper;
import com.scoperetail.automata.example.event.QuikPikAutomataEvent;
import com.scoperetail.commons.json.util.JsonUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Optional;

import static com.scoperetail.automata.example.utils.FileReader.getMessage;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("example")
@SpringBootTest(classes = {AutomataCoreApplication.class})
@ExtendWith(SpringExtension.class)
public class TestClient {

  private static final String BASE_PATH = "src/test/resources/";
  @Autowired private FSMHandler fsmHandler;
  @Autowired private EventHelper eventHelper;

  @Test
  public void quikPikEvent() throws IOException, StateAutomataException {
    String json = getMessage(BASE_PATH + "json/quikpik/quikpik_1.json");
    AutomataEvent automataEvent =
        JsonUtils.unmarshal(
            Optional.ofNullable(json), Optional.of(new TypeReference<QuikPikAutomataEvent>() {}));
    assertNotNull(automataEvent);

    PendingEvent e = eventHelper.getAutomataEventForMessage(automataEvent);
    assertNotNull(e);
    fsmHandler.onEvent(e);
  }
}

package com.scoperetail.automata.example.client;

import com.scoperetail.automata.core.AutomataCoreApplication;
import com.scoperetail.automata.core.exception.StateAutomataException;
import com.scoperetail.automata.core.fsm.FSMHandler;
import com.scoperetail.automata.core.helper.EventHelper;
import com.scoperetail.automata.core.persistence.entity.PendingEvent;
import com.scoperetail.automata.core.spi.AutomataEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static com.scoperetail.automata.example.utils.AutomataEventFactory.from;
import static com.scoperetail.automata.example.utils.FileReader.getMessage;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    AutomataEvent automataEvent = from(json);

    PendingEvent e = eventHelper.getAutomataEventForMessage(automataEvent);
    assertNotNull(e);
    fsmHandler.onEvent(e);
  }

  @Test
  public void quikPikFutureEvent() throws IOException, StateAutomataException {
    String json1 = getMessage(BASE_PATH + "json/quikpik/quikpik_1.json");
    AutomataEvent automataEvent1 = from(json1);
    String json2 = getMessage(BASE_PATH + "json/quikpik/quikpik_2.json");
    AutomataEvent automataEvent2 = from(json2);

    PendingEvent e1 = eventHelper.getAutomataEventForMessage(automataEvent1);
    PendingEvent e2 = eventHelper.getAutomataEventForMessage(automataEvent2);
    assertNotNull(e1);
    assertNotNull(e2);
    fsmHandler.onEvent(e1);
    fsmHandler.onEvent(e2);
    fsmHandler.onEvent(e2);
  }
}

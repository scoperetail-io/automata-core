package com.scoperetail.automata.example.client;

import com.scoperetail.automata.core.AutomataCoreApplication;
import com.scoperetail.automata.core.client.spi.Automata;
import com.scoperetail.automata.core.client.spi.AutomataEvent;
import com.scoperetail.automata.core.exception.StateAutomataException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static com.scoperetail.automata.example.utils.AutomataEventFactory.from;
import static com.scoperetail.automata.example.utils.FileReader.getMessage;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("example")
@SpringBootTest(classes = {AutomataCoreApplication.class})
@ExtendWith(SpringExtension.class)
public class TestClient {

  private static final String BASE_PATH = "src/test/resources/";
  @Autowired private Automata automata;

  @Test
  public void quikPikEvent() throws IOException, StateAutomataException {
    String json = getMessage(BASE_PATH + "json/quikpik/quikpik_1.json");
    AutomataEvent automataEvent = from(json);
    assertNotNull(automataEvent);
    assertAll(() -> automata.processEvent(automataEvent));
  }

  @Test
  public void quikPikFutureEvent() throws IOException, StateAutomataException {
    String json1 = getMessage(BASE_PATH + "json/quikpik/quikpik_1.json");
    AutomataEvent automataEvent1 = from(json1);
    String json2 = getMessage(BASE_PATH + "json/quikpik/quikpik_2.json");
    AutomataEvent automataEvent2 = from(json2);

    assertNotNull(automataEvent1);
    assertAll(() -> automata.processEvent(automataEvent1));
    assertNotNull(automataEvent2);
    assertAll(() -> automata.processEvent(automataEvent2));
    assertNotNull(automataEvent2);
    assertAll(() -> automata.processEvent(automataEvent2));
  }
}

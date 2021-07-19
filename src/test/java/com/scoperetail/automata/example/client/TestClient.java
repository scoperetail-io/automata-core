package com.scoperetail.automata.example.client;

/*-
 * *****
 * automata-core
 * -----
 * Copyright (C) 2018 - 2021 Scope Retail Systems Inc.
 * -----
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * =====
 */

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

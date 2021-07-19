package com.scoperetail.automata.example.utils;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.scoperetail.automata.core.client.spi.AutomataEvent;
import com.scoperetail.automata.example.event.QuikPikAutomataEvent;
import com.scoperetail.commons.json.JsonUtils;

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

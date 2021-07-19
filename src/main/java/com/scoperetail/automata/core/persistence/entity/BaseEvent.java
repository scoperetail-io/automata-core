package com.scoperetail.automata.core.persistence.entity;

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

import com.scoperetail.automata.core.client.spi.AutomataEvent;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

/** @author scoperetail */
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseEvent {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private long id;

  @Column(name = "create_ts")
  @CreationTimestamp
  private Timestamp createTS;

  @Column(name = "delete_ts")
  private Timestamp deleteTS;

  @Column(length = 30, name = "event_name")
  private String eventName;

  @Column(length = 20, name = "entity_id")
  private String entityId;

  @Column(length = 255, name = "payload")
  private String payload;

  @Column(length = 30, name = "automata_name")
  private String automataName;

  @Column(length = 255, name = "lookup_key")
  private String lookupKey;

  @Column(name = "retry_count")
  private long retryCount;

  public BaseEvent(final AutomataEvent automataEvent, final String automataName) {
    this.entityId = automataEvent.getId();
    this.lookupKey = automataEvent.primaryLookupKey();
    this.automataName = automataName;
    this.payload = automataEvent.getPayload();
    this.eventName = automataEvent.getEventName();
  }

  public BaseEvent(final PendingEvent event) {
    this.entityId = event.getEntityId();
    this.lookupKey = event.getLookupKey();
    this.automataName = event.getAutomataName();
    this.payload = event.getPayload();
    this.eventName = event.getEventName();
  }
}

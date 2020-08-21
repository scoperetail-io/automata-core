package com.scoperetail.automata.core.persistence.entity;

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

  @Column(length = 20, name = "key")
  private String key;

  @Column(length = 255, name = "payload")
  private String payload;

  @Column(length = 30, name = "automata_name")
  private String automataName;

  @Column(length = 255, name = "lookup_key")
  private String lookupKey;

  @Column(name = "retry_count")
  private long retryCount;

  public BaseEvent(final AutomataEvent automataEvent, final String automataName) {
    this.key = automataEvent.getId();
    this.lookupKey = automataEvent.primaryLookupKey();
    this.automataName = automataName;
    this.payload = automataEvent.getPayload();
    this.eventName = automataEvent.getEventName();
  }

  public BaseEvent(final PendingEvent event) {
    this.key = event.getKey();
    this.lookupKey = event.getLookupKey();
    this.automataName = event.getAutomataName();
    this.payload = event.getPayload();
    this.eventName = event.getEventName();
  }
}

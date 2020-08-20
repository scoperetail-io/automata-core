package com.scoperetail.automata.core.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

/** @author scoperetail */
@Entity(name = "SuccessEvent")
@Table(name = "success_event")
// Event,RejectedEvent and SuccessEvent have similar attributes
@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class SuccessEvent {
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

  public static SuccessEvent of(final Event event) {
    return SuccessEvent.builder()
        .payload(event.getPayload())
        .eventName(event.getEventName())
        .key(event.getKey())
        .lookupKey(event.getLookupKey())
        .automataName(event.getAutomataName())
        .build();
  }
}

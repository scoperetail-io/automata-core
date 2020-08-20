package com.scoperetail.automata.core.model;

import com.scoperetail.automata.core.utility.OlcmEvent;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

/** @author scoperetail */
@Entity(name = "PendingEvent")
@Table(name = "pending_event")
@NamedQuery(
    name = "Event.findByKeySortByCreateTS",
    query = "select e from PendingEvent e where e.key like :key order by e.createTS ASC")
// Event,RejectedEvent and SuccessEvent have similar attributes
@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class Event {
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

  public static Event of(final OlcmEvent order, final String automataName) {
    return Event.builder()
        .payload(order.getPayload())
        .eventName(order.getEventName())
        .key(order.getKey())
        .lookupKey(order.getLookupKey())
        .automataName(automataName)
        .build();
  }
}

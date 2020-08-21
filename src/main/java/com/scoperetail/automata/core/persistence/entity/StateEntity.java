package com.scoperetail.automata.core.persistence.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

/** @author scoperetail */
@Entity(name = "StateEntity")
@Table(name = "state_entity")
@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class StateEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "state_name", length = 30)
  private String stateName;

  @Column(name = "create_ts", nullable = false)
  @CreationTimestamp
  private Timestamp createTS;

  @Column(name = "delete_ts")
  private Timestamp deleteTS;

  @Column(name = "update_ts")
  @UpdateTimestamp
  private Timestamp updateTS;

  @Column(unique = true, length = 20, name = "key")
  @EqualsAndHashCode.Include
  private String key;

  @Column(name = "automata_type", length = 30)
  private String automataType;

  @Column(length = 255, name = "lookup_key")
  private String lookupKey;
}

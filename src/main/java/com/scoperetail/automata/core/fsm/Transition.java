package com.scoperetail.automata.core.fsm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Transition {

  private String onEvent;
  private String toState;
}

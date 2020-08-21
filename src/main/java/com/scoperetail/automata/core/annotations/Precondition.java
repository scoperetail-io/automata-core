package com.scoperetail.automata.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** @author scoperetail */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Precondition {
  Transition transition();
}
package com.scoperetail.automata.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** @author scoperetail */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Automata {

  String name();

  Class<?> clazz();

  Class<?> service();

  String startState();

  String[] endState();

  Transition[] transitions();

  State[] states();

  Event[] events();
}

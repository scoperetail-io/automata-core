package com.scoperetail.automata.core.persistence.entity;

import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.FieldPredicate.include;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

class BaseEventTest {

  @Test
  public void shouldPassAllPojoTests() {
    // given
    final Class<?> classUnderTest = BaseEvent.class;
    // then
    assertPojoMethodsFor(classUnderTest)
        .testing(Method.GETTER, Method.SETTER)
        .testing(Method.CONSTRUCTOR)
        .areWellImplemented();
    assertPojoMethodsFor(classUnderTest, include("id"))
        .testing(Method.EQUALS, Method.HASH_CODE)
        .areWellImplemented();
  }
}

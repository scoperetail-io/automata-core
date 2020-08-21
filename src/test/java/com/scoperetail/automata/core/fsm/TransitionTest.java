package com.scoperetail.automata.core.fsm;

import org.junit.jupiter.api.Test;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

class TransitionTest {
    @Test
    public void shouldPassAllPojoTests() {
        // given
        final Class<?> classUnderTest = Transition.class;
        // then
        assertPojoMethodsFor(classUnderTest)
                .areWellImplemented();
    //        assertPojoMethodsFor(classUnderTest, include("id"))
    //                .testing(Method.EQUALS, Method.HASH_CODE)
    //                .areWellImplemented();
  }
}
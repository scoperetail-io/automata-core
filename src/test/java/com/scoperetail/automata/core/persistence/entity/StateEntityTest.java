package com.scoperetail.automata.core.persistence.entity;

import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.assertion.Method;

import static org.junit.jupiter.api.Assertions.*;
import static pl.pojo.tester.api.FieldPredicate.include;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

class StateEntityTest {
    @Test
    public void shouldPassAllPojoTests() {
        // given
        final Class<?> classUnderTest = StateEntity.class;
        // then
        assertPojoMethodsFor(classUnderTest)
                .testing(Method.GETTER, Method.SETTER)
                .testing(Method.CONSTRUCTOR)
                .areWellImplemented();
        assertPojoMethodsFor(classUnderTest, include("entityId"))
                .testing(Method.EQUALS, Method.HASH_CODE)
                .areWellImplemented();
    }

}
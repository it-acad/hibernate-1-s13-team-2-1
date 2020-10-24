package com.softserve.itacademy.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StateTests {

    @ParameterizedTest
    @MethodSource("provideInvalidStateName")
    void constraintViolationRoleName(String input, String errorValue) {
        State state = new State();
        state.setName(input);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<State>> violations = validator.validate(state);
        assertEquals(1, violations.size());
        assertEquals(errorValue, violations.iterator().next().getInvalidValue());
    }

    private static Stream<Arguments> provideInvalidStateName(){
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of("", ""),
                Arguments.of("Invalid state #1", "Invalid state #1"),
                Arguments.of("State-State-State-Value", "State-State-State-Value")
        );
    }

    // Coverage and sanity check
    @Test
    void statesEquals() {
        State state = new State();
        state.setName("Valid-state-name");
        state.setTasks(Arrays.asList());

        State anotherState = new State();
        anotherState.setName("Valid-state-name");
        anotherState.setTasks(Arrays.asList());

        assertEquals(state, anotherState);
        assertEquals(state.toString(), anotherState.toString());
        assertEquals(state.hashCode(), anotherState.hashCode());
    }
}

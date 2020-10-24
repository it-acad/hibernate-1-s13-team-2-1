package com.softserve.itacademy.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TaskTests {

    private static Task validTask;
    private static State validState;

    @BeforeAll
    static void init() {
        validState = new State();
        validState.setName("In progress");

        validTask = new Task();
        validTask.setName("Do shopping in mall at Quin 3rd Avenue / code(_+*$)");
        validTask.setPriority(Priority.LOW);
        validTask.setState(validState);

    }

    @Test
    void createValidTask() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Task>> violations = validator.validate(validTask);

        assertEquals(0, violations.size());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidName")
    void constraintViolationInvalidName(String input, String errorValue) {
        Task task = new Task();
        task.setState(validState);
        task.setName(input);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Task>> violations = validator.validate(task);
        assertEquals(1, violations.size());
        assertEquals(errorValue, violations.iterator().next().getInvalidValue());
    }

    private static Stream<Arguments> provideInvalidName(){
        return Stream.of(
                Arguments.of("", ""),
                Arguments.of("A", "A"),
                Arguments.of("AA", "AA")
        );
    }

    // coverage and sanity check
    @Test
    void tasksEquals() {
        Task anotherValidTask = new Task();
        anotherValidTask.setName("Do shopping in mall at Quin 3rd Avenue / code(_+*$)");
        anotherValidTask.setPriority(Priority.LOW);
        anotherValidTask.setState(validState);

        assertEquals(validTask, anotherValidTask);
        assertEquals(validTask.toString(), anotherValidTask.toString());
        assertEquals(validTask.hashCode(), anotherValidTask.hashCode());
    }
}

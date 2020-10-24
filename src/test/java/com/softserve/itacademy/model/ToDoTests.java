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
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ToDoTests {

    private static Role mentorRole;
    private static Role traineeRole;
    private static User validUser;

    @BeforeAll
    static void init() {
        mentorRole = new Role();
        mentorRole.setName("MENTOR");
        traineeRole = new Role();
        traineeRole.setName("TRAINEE");
        validUser  = new User();
        validUser.setEmail("valid@cv.edu.ua");
        validUser.setFirstName("Valid-Name");
        validUser.setLastName("Valid-Name");
        validUser.setPassword("qwQW12!@");
        validUser.setRole(traineeRole);
    }

    @Test
    void userWithValidToDo() {
        User user = new User();
        user.setEmail("rty5@i.ua");
        user.setFirstName("Valid-Name");
        user.setLastName("Valid-Name");
        user.setPassword("qwQW12!@");
        user.setRole(traineeRole);

        ToDo toDo = new ToDo();
        toDo.setOwner(user);
        toDo.setTitle("Shopping List");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(0, violations.size());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidTitleToDo")
    void constraintViolationInvalidTitle(String input, String errorValue) {

        ToDo toDo = new ToDo();
        toDo.setTitle(input);
        toDo.setCreatedAt(LocalDateTime.now());
        toDo.setOwner(validUser);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<ToDo>> violations = validator.validate(toDo);
        assertEquals(1, violations.size());
        assertEquals(errorValue, violations.iterator().next().getInvalidValue());
    }

    private static Stream<Arguments> provideInvalidTitleToDo(){
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of("", "")
        );
    }

    // Coverage and sanity check
    @Test
    void toDosEquals() {
        String title = "Shopping list";

        ToDo toDo = new ToDo();
        toDo.setTitle(title);
        toDo.setOwner(validUser);

        ToDo toDo2 = new ToDo();
        toDo2.setTitle(title);
        toDo2.setOwner(validUser);

        assertEquals(toDo, toDo2);
        assertEquals(toDo.toString(), toDo2.toString());
        assertEquals(toDo.hashCode(), toDo2.hashCode());
    }

}

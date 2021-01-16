package com.example.myrulescardgamebackend.rest.validators;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import com.example.myrulescardgamebackend.rest.domain.User;
import com.example.myrulescardgamebackend.rest.repositories.UserRepository;
import com.example.myrulescardgamebackend.rest.services.UserService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserValidatorTest {
    UserValidator userValidator;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserValidatorTest() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        User testUser = new User();
        testUser.setUsername("testName");
        Mockito.when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));

        UserService userService = new UserService(userRepository, bCryptPasswordEncoder);
        this.userValidator = new UserValidator(userService, new PasswordValidator());

    }

    public void setUp() {

    }

    @Test
    public void validateWithExistingUser() {
        User inputUser = new User();
        inputUser.setUsername("testName");
        inputUser.setPassword("password");
        Result expectedOutput = new Error("User is already present");

        Result result = userValidator.validate(inputUser);

        assertThat(result.isSuccess()).isEqualTo(expectedOutput.isSuccess());
        assertThat(result.getMessage()).isEqualTo(expectedOutput.getMessage());
    }

    @Test
    public void validateWithNoUsername() {
        User inputUser = new User();
        inputUser.setPassword("password");
        Result expectedOutput = new Error("No valid name was provided");

        Result result = userValidator.validate(inputUser);

        assertThat(result.isSuccess()).isEqualTo(expectedOutput.isSuccess());
        assertThat(result.getMessage()).isEqualTo(expectedOutput.getMessage());
    }

    @Test
    public void validateWithToShortUsername() {
        User inputUser = new User();
        inputUser.setUsername("ts");
        inputUser.setPassword("password");
        Result expectedOutput = new Error("Username is too short");

        Result result = userValidator.validate(inputUser);

        assertThat(result.isSuccess()).isEqualTo(expectedOutput.isSuccess());
        assertThat(result.getMessage()).isEqualTo(expectedOutput.getMessage());
    }

    @Test
    public void validateWithToLongUsername() {
        User inputUser = new User();
        inputUser.setUsername("thisIsTHeTooLongUsernameThisIsTHeTooLongUsername");
        inputUser.setPassword("password");
        Result expectedOutput = new Error("Username is too long");

        Result result = userValidator.validate(inputUser);

        assertThat(result.isSuccess()).isEqualTo(expectedOutput.isSuccess());
        assertThat(result.getMessage()).isEqualTo(expectedOutput.getMessage());
    }

    @Test
    public void validateWithValidUser() {
        User inputUser = new User();
        inputUser.setUsername("newTestName");
        inputUser.setPassword("password");
        Result expectedOutput = new Success("Successfully registered user");

        Result result = userValidator.validate(inputUser);

        assertThat(result.isSuccess()).isEqualTo(expectedOutput.isSuccess());
        assertThat(result.getMessage()).isEqualTo(expectedOutput.getMessage());
    }
}

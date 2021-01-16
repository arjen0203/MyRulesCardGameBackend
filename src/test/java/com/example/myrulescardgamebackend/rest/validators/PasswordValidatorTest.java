package com.example.myrulescardgamebackend.rest.validators;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class PasswordValidatorTest {
    PasswordValidator passwordValidator;
    public PasswordValidatorTest() {
        this.passwordValidator = new PasswordValidator();
    }


    @Test
    public void validateWithCorrectPasswordTest() {
        String password = "thisIsAvalidPassword";
        Result expectedOutput = new Success("Password is correct");

        Result result = passwordValidator.validate(password);

        assertThat(result.isSuccess()).isEqualTo(expectedOutput.isSuccess());
        assertThat(result.getMessage()).isEqualTo(expectedOutput.getMessage());
    }

    @Test
    public void validateWithNoPasswordTest() {
        String password = null;
        Result expectedOutput = new Error("No password was provided");

        Result result = passwordValidator.validate(password);

        assertThat(result.isSuccess()).isEqualTo(expectedOutput.isSuccess());
        assertThat(result.getMessage()).isEqualTo(expectedOutput.getMessage());
    }

    @Test
    public void validateWithTooShortPasswordTest() {
        String password = "yes";
        Result expectedOutput = new Error("Password is too short");

        Result result = passwordValidator.validate(password);

        assertThat(result.isSuccess()).isEqualTo(expectedOutput.isSuccess());
        assertThat(result.getMessage()).isEqualTo(expectedOutput.getMessage());
    }

    @Test
    public void validateWithTooLongPasswordTest() {
        String password = "thisIsTHeTooLongPasswordthisIsTHeTooLongPasswordthisIsTHeTooLongPassword";
        Result expectedOutput = new Error("Password is too long");

        Result result = passwordValidator.validate(password);

        assertThat(result.isSuccess()).isEqualTo(expectedOutput.isSuccess());
        assertThat(result.getMessage()).isEqualTo(expectedOutput.getMessage());
    }
}

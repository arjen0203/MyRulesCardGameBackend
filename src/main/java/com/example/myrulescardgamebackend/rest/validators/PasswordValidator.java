package com.example.myrulescardgamebackend.rest.validators;

import org.springframework.stereotype.Component;

@Component
public class PasswordValidator {

    public Result validate(String password) {
        if (password == null) {
            return new Error("No password was provided");
        }

        if (password.length() < 6) {
            return new Error("Password is too short");
        }

        if (password.length() > 64) {
            return new Error("Password is too long");
        }

        return new Success("Password is correct");
    }
}
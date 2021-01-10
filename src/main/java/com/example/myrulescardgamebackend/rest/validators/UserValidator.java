package com.example.myrulescardgamebackend.rest.validators;

import com.example.myrulescardgamebackend.rest.domain.User;
import com.example.myrulescardgamebackend.rest.services.UserService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class UserValidator {

    private UserService userService;
    private PasswordValidator passwordValidator;

    public UserValidator(UserService userService, PasswordValidator passwordValidator) {
        this.userService = userService;
        this.passwordValidator = passwordValidator;
    }

    public Result validate(User user) {
        var username = user.getUsername();

        if (username == null || !StringUtils.hasText(user.getUsername())) {
            return new Error("No valid name was provided");
        }

        if (username.length() < 3) {
            return new Error("Username is too short");
        }

        if (username.length() > 32) {
            return new Error("Username is too long");
        }

        var result = validatePassword(user.getPassword());
        if (!result.isSuccess()) {
            return new Error(result.getMessage());
        }

        if (userService.findByUsername(user.getUsername()).isPresent()) {
            return new Error("User is already present");
        }

        return new Success("Successfully registered user");
    }

    public Result validatePassword(String password) {
        return passwordValidator.validate(password);
    }
}
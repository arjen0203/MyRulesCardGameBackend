package com.example.myrulescardgamebackend.rest.controllers;

import java.util.Optional;

import javax.validation.Valid;

import com.auth0.jwt.JWT;
import com.example.myrulescardgamebackend.rest.domain.User;
import com.example.myrulescardgamebackend.rest.repositories.UserRepository;
import com.example.myrulescardgamebackend.rest.services.UserService;
import com.example.myrulescardgamebackend.rest.validators.UserValidator;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/user")
public class UserController {
    private final UserService userService;
    private final UserValidator userValidator;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserValidator userValidator, UserRepository userRepository) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<?> getMe(@RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
        String decodedToken = new String(Base64.decodeBase64(JWT.decode(token).getPayload()));
        try {
            JSONObject json = new JSONObject(decodedToken);
            String username = json.get("sub").toString();
            Optional<User> user = userRepository.findByUsername(username);
            if (user.isEmpty()) {
                return ResponseEntity.status(404).body("User not found");
            }
            return ResponseEntity.ok(user.get());
        } catch (JSONException e) {
            return ResponseEntity.status(403).body("Unauthorized request");
        }
    }

    @CrossOrigin
    @PostMapping(path = "/add")
    public ResponseEntity<?> addNewUser(@Valid @RequestBody User user) {
        var result = userValidator.validate(user);
        if (result.isSuccess()) {
            userService.registerUser(user);
            return ResponseEntity.ok("Successfully registered user");
        }

        return ResponseEntity.status(409).body(result.getMessage());
    }
}

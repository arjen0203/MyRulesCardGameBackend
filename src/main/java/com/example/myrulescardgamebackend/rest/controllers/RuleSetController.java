package com.example.myrulescardgamebackend.rest.controllers;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.auth0.jwt.JWT;
import com.example.myrulescardgamebackend.rest.domain.RuleSet;
import com.example.myrulescardgamebackend.rest.domain.RuleSetSimple;
import com.example.myrulescardgamebackend.rest.domain.User;
import com.example.myrulescardgamebackend.rest.repositories.RuleSetRepository;
import com.example.myrulescardgamebackend.rest.repositories.UserRepository;
import com.example.myrulescardgamebackend.rest.services.RuleSetService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.hibernate.criterion.Restrictions;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/rulesets")
public class RuleSetController {
    private final RuleSetRepository ruleSetRepository;
    private final UserRepository userRepository;
    private final RuleSetService ruleSetService;

    public RuleSetController(RuleSetRepository ruleSetRepository, UserRepository userRepository, RuleSetService ruleSetService) {
        this.ruleSetRepository = ruleSetRepository;
        this.userRepository = userRepository;
        this.ruleSetService = ruleSetService;
    }

    @CrossOrigin
    @Transactional
    @PostMapping(path="/add")
    public ResponseEntity<?> addNewRuleSet(@RequestHeader("Authorization") String token,
            @Valid @RequestBody RuleSet ruleSet) {
        token = token.replace("Bearer ", "");
        String decodedToken = new String(Base64.decodeBase64(JWT.decode(token).getPayload()));
        try {
            JSONObject json = new JSONObject(decodedToken);
            String username = json.get("sub").toString();
            Optional<User> user = userRepository.findByUsername(username);
            if (user.isEmpty()) return ResponseEntity.status(404).body("User not found");
            ruleSet.setUser(user.get());
            ruleSetRepository.save(ruleSet);
            return ResponseEntity.ok(ruleSet);
        } catch (JSONException e) {
            return ResponseEntity.status(403).body("Unauthorized request");
        }
    }

    @CrossOrigin
    @GetMapping("/get")
    public ResponseEntity<?> getRuleSet(@RequestParam int id) {
        var ruleSet = ruleSetService.getRuleSetById(id);
        if (ruleSet.isPresent()) {
            return ResponseEntity.ok(ruleSet.get());
        }
        return ResponseEntity.status(404).body("Ruleset not found");
    }

    @CrossOrigin
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllRuleSetOfUser(@RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
        String decodedToken = new String(Base64.decodeBase64(JWT.decode(token).getPayload()));
        try {
            JSONObject json = new JSONObject(decodedToken);
            String username = json.get("sub").toString();
            Optional<User> user = userRepository.findByUsername(username);
            if (user.isEmpty()) return ResponseEntity.status(404).body("User not found");
            List<RuleSetSimple> ruleSets = ruleSetRepository.findRuleSetByUser(user);
            return ResponseEntity.ok(ruleSets);
        } catch (JSONException e) {
            return ResponseEntity.status(403).body("Unauthorized request");
        }



        //return ResponseEntity.status(404).body("Ruleset not found");
    }
}

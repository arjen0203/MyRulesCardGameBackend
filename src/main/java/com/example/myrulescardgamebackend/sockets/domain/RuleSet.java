package com.example.myrulescardgamebackend.sockets.domain;

import java.util.ArrayList;

public class RuleSet {
    ArrayList<RuleAndCards> cardRules;

    public RuleSet() {

    }

    public enum RuleEnum {
        SKIP,
        PICK
    }
}

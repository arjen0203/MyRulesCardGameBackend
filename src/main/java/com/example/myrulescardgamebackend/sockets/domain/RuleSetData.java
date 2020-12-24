package com.example.myrulescardgamebackend.sockets.domain;

import java.util.ArrayList;

public class RuleSetData {
    ArrayList<RuleAndCards> cardRules;

    public RuleSetData() {

    }

    public enum RuleEnum {
        SKIP,
        PICK
    }
}

package com.example.myrulescardgamebackend.sockets.games.rules;

import java.util.ArrayList;

public class RuleSet {
    ArrayList<Rule> rules;

    public RuleSet(ArrayList<Rule> rules) {
        this.rules = rules;
    }

    public ArrayList<Rule> getRules() {
        return rules;
    }
}

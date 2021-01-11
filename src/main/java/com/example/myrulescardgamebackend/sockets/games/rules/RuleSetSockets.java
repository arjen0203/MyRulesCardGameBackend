package com.example.myrulescardgamebackend.sockets.games.rules;

import java.util.ArrayList;

public class RuleSetSockets {
    ArrayList<Rule> rules;

    public RuleSetSockets(ArrayList<Rule> rules) {
        this.rules = rules;
    }

    public ArrayList<Rule> getRules() {
        return rules;
    }
}

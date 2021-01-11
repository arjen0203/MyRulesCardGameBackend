package com.example.myrulescardgamebackend.sockets.domain;

import java.util.ArrayList;

public class RuleSetData {
    ArrayList<RuleAndCards> cardRules;

    public RuleSetData() {

    }

    public RuleSetData(ArrayList<RuleAndCards> cardRules) {
        this.cardRules = cardRules;
    }

    public ArrayList<RuleAndCards> getCardRules() {
        return cardRules;
    }


}

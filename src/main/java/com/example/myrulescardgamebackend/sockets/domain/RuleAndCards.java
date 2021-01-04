package com.example.myrulescardgamebackend.sockets.domain;

import java.util.ArrayList;

public class RuleAndCards {
    RuleSetData.RuleEnum ruleId;
    ArrayList<Card> cards;

    public RuleAndCards() {}

    public RuleAndCards(RuleSetData.RuleEnum ruleId, ArrayList<Card> cards) {
        this.ruleId = ruleId;
        this.cards = cards;
    }

    public RuleSetData.RuleEnum getRuleId() {
        return ruleId;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
}

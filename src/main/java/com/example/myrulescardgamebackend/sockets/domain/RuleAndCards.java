package com.example.myrulescardgamebackend.sockets.domain;

import java.util.ArrayList;

import com.example.myrulescardgamebackend.RuleEnum;

public class RuleAndCards {
    RuleEnum ruleId;
    ArrayList<SocketCard> socketCardDomains;

    public RuleAndCards() {}

    public RuleAndCards(RuleEnum ruleId, ArrayList<SocketCard> socketCardDomains) {
        this.ruleId = ruleId;
        this.socketCardDomains = socketCardDomains;
    }

    public RuleEnum getRuleId() {
        return ruleId;
    }

    public ArrayList<SocketCard> getCards() {
        return socketCardDomains;
    }
}

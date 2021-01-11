package com.example.myrulescardgamebackend;

public enum RuleEnum {
    SKIP(0),
    PICK1(1),
    PICK2(2),
    PICK4(3),
    REVERSE(4);

    private final int rule;

    public int getRule() {
        return this.rule;
    }

    RuleEnum(int rule) {
        this.rule = rule;
    }
}

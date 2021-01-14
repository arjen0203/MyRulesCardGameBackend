package com.example.myrulescardgamebackend;

public enum RuleEnum {
    REVERSE(0), PICK1(1), PICK2(2), PICK4(3), SKIP(4),
    ;

    private final int rule;

    public int getRule() {
        return this.rule;
    }

    RuleEnum(int rule) {
        this.rule = rule;
    }
}

package com.example.myrulescardgamebackend.rest.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int suitEnum;
    private int valueEnum;

    public void setSuitEnum(int suitEnum) {
        this.suitEnum = suitEnum;
    }

    public void setValueEnum(int valueEnum) {
        this.valueEnum = valueEnum;
    }

    public int getSuitEnum() {
        return suitEnum;
    }

    public int getValueEnum() {
        return valueEnum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

package com.example.myrulescardgamebackend.rest.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class CardRule {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    private int ruleEnum;

    @OneToMany(cascade = { CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<Card> cards;


    public void setRuleEnum(int ruleEnum) {
        this.ruleEnum = ruleEnum;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public int getRuleEnum() {
        return ruleEnum;
    }

    public List<Card> getCards() {
        return cards;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

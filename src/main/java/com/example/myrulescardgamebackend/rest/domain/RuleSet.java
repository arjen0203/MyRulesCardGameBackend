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

@Entity
public class RuleSet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;

    public String name;

    @ManyToOne
    private User user;

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    private List<CardRule> cardRules;

    public void setUser(User user) {
        this.user = user;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCardRules(List<CardRule> cardRules) {
        this.cardRules = cardRules;
    }

    public String getName() {
        return name;
    }

    public List<CardRule> getCardRules() {
        return cardRules;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

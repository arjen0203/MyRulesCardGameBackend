package com.example.myrulescardgamebackend.rest.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.myrulescardgamebackend.rest.domain.CardRule;
import com.example.myrulescardgamebackend.rest.domain.RuleSet;
import com.example.myrulescardgamebackend.rest.repositories.CardRuleRepository;
import com.example.myrulescardgamebackend.rest.repositories.RuleSetRepository;
import org.springframework.stereotype.Service;

@Service
public class RuleSetService {
    RuleSetRepository ruleSetRepository;
    CardRuleRepository cardRuleRepository;

    public RuleSetService(RuleSetRepository ruleSetRepository, CardRuleRepository cardRuleRepository) {
        this.ruleSetRepository = ruleSetRepository;
        this.cardRuleRepository = cardRuleRepository;
    }

    public Optional<RuleSet> getRuleSetById(int id) {
        //todo if time find better fix
        var ruleSet = ruleSetRepository.findById(id);
        if (ruleSet.isEmpty()) {
            return ruleSet;
        }
        List<CardRule> filteredRules = new ArrayList<>();
        for (CardRule cardRule : ruleSet.get().getCardRules()) {
            boolean exists = false;
            for (CardRule filteredRule : filteredRules) {
                if (cardRule.getId() == filteredRule.getId()) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                filteredRules.add(cardRule);
            }
        }
        ruleSet.get().setCardRules(filteredRules);

        return ruleSet;
    }
}

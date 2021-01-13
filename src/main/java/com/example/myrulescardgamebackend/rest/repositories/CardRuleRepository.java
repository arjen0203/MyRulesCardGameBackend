package com.example.myrulescardgamebackend.rest.repositories;

import java.util.List;

import com.example.myrulescardgamebackend.rest.domain.CardRule;
import com.example.myrulescardgamebackend.rest.domain.RuleSet;
import org.springframework.data.repository.CrudRepository;

public interface CardRuleRepository extends CrudRepository<CardRule, Integer> {
}

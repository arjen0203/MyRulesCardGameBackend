package com.example.myrulescardgamebackend.rest.repositories;

import com.example.myrulescardgamebackend.rest.domain.CardRule;
import org.springframework.data.repository.CrudRepository;

public interface CardRuleRepository extends CrudRepository<CardRule, Integer> {
}

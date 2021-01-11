package com.example.myrulescardgamebackend.rest.repositories;


import java.util.List;
import java.util.Optional;

import com.example.myrulescardgamebackend.rest.domain.RuleSet;
import com.example.myrulescardgamebackend.rest.domain.RuleSetSimple;
import com.example.myrulescardgamebackend.rest.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RuleSetRepository extends CrudRepository<RuleSet, Integer> {

    List<RuleSetSimple> findRuleSetByUser(Optional<User> user);
}


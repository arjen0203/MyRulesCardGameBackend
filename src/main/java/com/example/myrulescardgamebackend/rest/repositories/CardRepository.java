package com.example.myrulescardgamebackend.rest.repositories;

import com.example.myrulescardgamebackend.rest.domain.Card;
import org.springframework.data.repository.CrudRepository;

public interface CardRepository extends CrudRepository<Card, Integer> {
}
package com.example.myrulescardgamebackend.rest.repositories;

import java.util.Optional;

import com.example.myrulescardgamebackend.rest.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
}

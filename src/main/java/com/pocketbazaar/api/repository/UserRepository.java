package com.pocketbazaar.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pocketbazaar.api.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
}

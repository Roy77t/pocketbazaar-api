package com.pocketbazaar.api.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.pocketbazaar.api.model.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    // Additional custom queries (if needed) can be added here
}


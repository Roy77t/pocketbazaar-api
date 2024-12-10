package com.pocketbazaar.api.repository;

import com.pocketbazaar.api.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    // Fetch products by user email
    List<Product> findByUserId(String userId);
}

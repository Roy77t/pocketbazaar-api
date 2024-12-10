package com.pocketbazaar.api.repository;


import com.pocketbazaar.api.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByUserId(String userId); // Get orders by user ID
}


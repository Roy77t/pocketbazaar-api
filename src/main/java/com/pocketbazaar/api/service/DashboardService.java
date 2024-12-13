package com.pocketbazaar.api.service;


import com.pocketbazaar.api.repository.ProductRepository;
import com.pocketbazaar.api.repository.UserRepository;
import com.pocketbazaar.api.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    public long getTotalProducts() {
        return productRepository.count(); 
    }

    public long getTotalUser() {
        return userRepository.count();
    }

    public long getTotalOrders() {
        return orderRepository.count();
    }
}


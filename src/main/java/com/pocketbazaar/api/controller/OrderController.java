package com.pocketbazaar.api.controller;

import com.pocketbazaar.api.model.Order;
import com.pocketbazaar.api.model.OrderRequest;
import com.pocketbazaar.api.service.OrderService;
import com.pocketbazaar.api.util.CurrentUserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Endpoint to create a new order
    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest orderRequest) {
        String userId = CurrentUserSession.getCurrentUserDetails().getUsername(); 

        // Create the order with address details passed in the request body
        Order order = orderService.createOrder(
            userId,
            orderRequest.getName(),
            orderRequest.getMobile(),
            orderRequest.getPincode(),
            orderRequest.getDistrict(),
            orderRequest.getAddress(),
            orderRequest.getLocality(),
            orderRequest.getState()
        );
        return ResponseEntity.ok(order);
    }

    // Endpoint to get all orders of the current user
    @GetMapping("/my-orders")
    public ResponseEntity<List<Order>> getUserOrders() {
        // Get the current user from JWT token
        String userId = CurrentUserSession.getCurrentUserDetails().getUsername(); 

        List<Order> orders = orderService.getUserOrders(userId);
        return ResponseEntity.ok(orders);
    }
}

package com.pocketbazaar.api.service;


import com.pocketbazaar.api.model.Order;
import com.pocketbazaar.api.model.Cart;
import com.pocketbazaar.api.model.CartItem;
import com.pocketbazaar.api.repository.CartRepository;
import com.pocketbazaar.api.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    public Order createOrder(String userId) {
    // Step 1: Cart ko unwrap karte hain
    Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

    // Step 2: Cart se items ko get karte hain
    List<CartItem> cartItems = cart.getItems();

    // Agar cart empty hai to order nahi banega
    if (cartItems.isEmpty()) {
        throw new RuntimeException("Cart is empty, cannot create order.");
    }

    // Step 3: Total amount calculate karte hain
    double totalAmount = cartItems.stream()
            .mapToDouble(item -> item.getPrice() * item.getQuantity())
            .sum();

    // Step 4: Order ko create karte hain
    Order order = new Order();
    order.setUserId(userId); 
    order.setItems(cartItems);  // Cart ke items ko order ke items mein convert karte hain
    // order.setAddressId(addressId);  // Selected address
    order.setStatus("DELIVERED");  // Initial status
    order.setTotalAmount(totalAmount);  // Total cost of the order
    order.setOrderDate(new Date());  // Current date as order date

    // Step 5: Order ko save karte hain
    Order savedOrder = orderRepository.save(order);

    // Step 6: Cart ko empty karte hain (cart ke items delete karte hain)
    cartRepository.delete(cart); // Pure cart ko delete kar rahe hain, ya items ko clear karna hoga

    return savedOrder;  // Return the created order
}

    public List<Order> getUserOrders(String userId) {
        return orderRepository.findByUserId(userId); // Fetch orders by userId
    }
}


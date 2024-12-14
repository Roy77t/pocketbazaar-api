package com.pocketbazaar.api.service;

import com.pocketbazaar.api.model.Order;
import com.pocketbazaar.api.model.Cart;
import com.pocketbazaar.api.model.CartItem;
import com.pocketbazaar.api.model.User;
import com.pocketbazaar.api.repository.CartRepository;
import com.pocketbazaar.api.repository.OrderRepository;
import com.pocketbazaar.api.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;  // Add UserRepository to fetch user details

    public Order createOrder(String userId, String name, String mobile, String pincode, String district, String address, String locality, String state) {
        // Step 1: Fetch user details using UserRepository
        User user = userRepository.findById(userId)
        
            .orElseThrow(() -> new RuntimeException("User not found"));

        // Get full name from user (combine firstname and lastname)
        String userName = user.getFullName();

        // Step 2: Fetch cart and cart items
        Cart cart = cartRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Cart not found"));

        List<CartItem> cartItems = cart.getItems();

        // Check if cart is empty
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty, cannot create order.");
        }

        // Step 3: Calculate total amount
        double totalAmount = cartItems.stream()
            .mapToDouble(item -> item.getPrice() * item.getQuantity())
            .sum();

        // Step 4: Create and populate the Order object
        Order order = new Order();
        order.setUserId(userId); 
        order.setUserName(userName);  // Set the user's full name here
        order.setItems(cartItems);  // Set cart items
        order.setStatus("PENDING");  // Set initial status
        order.setTotalAmount(totalAmount);  // Set the total order amount
        order.setOrderDate(new Date());  // Set current date as order date

        // Set address details
        order.setName(name);
        order.setMobile(mobile);
        order.setPincode(pincode);
        order.setDistrict(district);
        order.setAddress(address);
        order.setLocality(locality);
        order.setState(state);

        // Step 5: Save the order to the database
        Order savedOrder = orderRepository.save(order);

        // Step 6: Clear the cart (delete all items)
        cartRepository.delete(cart);

        return savedOrder;  // Return the created order
    }

    public List<Order> getUserOrders(String userId) {
        return orderRepository.findByUserId(userId);  // Fetch orders by userId
    }
}

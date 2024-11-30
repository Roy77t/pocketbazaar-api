// package com.pocketbazaar.api.controller;


// import com.pocketbazaar.api.model.Order;
// import com.pocketbazaar.api.model.OrderStatus;
// import com.pocketbazaar.api.repository.OrderRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.Date;
// import java.util.List;
// import java.util.Optional;

// @RestController
// @RequestMapping("/api/orders")
// public class OrderController {

//     @Autowired
//     private OrderRepository orderRepository;

//     // 1. Create Order
//     @PostMapping
//     public ResponseEntity<Order> createOrder(@RequestBody Order order) {
//         order.setCreatedAt(new Date());
//         order.setUpdatedAt(new Date());
//         order.setStatus(OrderStatus.PENDING); // Default status
//         Order savedOrder = orderRepository.save(order);
//         return ResponseEntity.ok(savedOrder);
//     }

//     // 2. Get Order by ID
//     @GetMapping("/{id}")
//     public ResponseEntity<Order> getOrderById(@PathVariable String id) {
//         Optional<Order> order = orderRepository.findById(id);
//         return order.map(ResponseEntity::ok)
//                 .orElse(ResponseEntity.notFound().build());
//     }

//     // 3. Get Orders by User ID (Order History)
//     @GetMapping("/user/{userId}")
//     public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable String userId) {
//         List<Order> orders = orderRepository.findByUserId(userId);
//         return ResponseEntity.ok(orders);
//     }

//     // 4. Update Order Status
//     @PutMapping("/{orderId}/status")
//     public ResponseEntity<Order> updateOrderStatus(@PathVariable String orderId, @RequestParam OrderStatus status) {
//         return orderRepository.findById(orderId).map(order -> {
//             order.setStatus(status); // Update status
//             order.setUpdatedAt(new Date());
//             Order updatedOrder = orderRepository.save(order);
//             return ResponseEntity.ok(updatedOrder);
//         }).orElse(ResponseEntity.notFound().build());
//     }

//     // 5. Delete Order
//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> deleteOrder(@PathVariable String id) {
//         orderRepository.deleteById(id);
//         return ResponseEntity.noContent().build();
//     }
// }



package com.pocketbazaar.api.controller;

import com.pocketbazaar.api.model.Order;
import com.pocketbazaar.api.model.Product;
import com.pocketbazaar.api.model.OrderStatus;
import com.pocketbazaar.api.repository.OrderRepository;
import com.pocketbazaar.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;  // Add this to fetch products

    // 1. Create Order
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        // Check if products are provided
        if (order.getProducts() == null || order.getProducts().isEmpty()) {
            return ResponseEntity.badRequest().body(null);  // Products cannot be null or empty
        }

        // Fetch actual Product objects from the database
        List<Product> productList = order.getProducts().stream()
                .map(product -> productRepository.findById(product.getId())
                        .orElseThrow(() -> new RuntimeException("Product not found: " + product.getId())))
                .collect(Collectors.toList());

        // Set the actual products in the order
        order.setProducts(productList);

        // Calculate total amount based on product prices
        double totalAmount = productList.stream()
                .mapToDouble(Product::getPrice)
                .sum();
        order.setTotalAmount(totalAmount);

        // Set timestamps and default status
        order.setCreatedAt(new Date());
        order.setUpdatedAt(new Date());
        order.setStatus(OrderStatus.PENDING);

        // Save the order
        Order savedOrder = orderRepository.save(order);
        return ResponseEntity.ok(savedOrder);
    }

    // 2. Get Order by ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable String id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. Get Orders by User ID (Order History)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable String userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    // 4. Update Order Status
    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable String orderId, @RequestParam OrderStatus status) {
        return orderRepository.findById(orderId).map(order -> {
            order.setStatus(status); // Update status
            order.setUpdatedAt(new Date());
            Order updatedOrder = orderRepository.save(order);
            return ResponseEntity.ok(updatedOrder);
        }).orElse(ResponseEntity.notFound().build());
    }

    // 5. Delete Order
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String id) {
        orderRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

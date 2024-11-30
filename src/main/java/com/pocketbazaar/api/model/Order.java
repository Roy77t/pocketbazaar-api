// package com.pocketbazaar.api.model;


// import org.springframework.data.annotation.Id;
// import org.springframework.data.mongodb.core.mapping.Document;

// import java.util.Date;
// import java.util.List;

// @Document(collection = "orders")
// public class Order {

//     @Id
//     private String id;
//     private String userId; // User ID for mapping
//     private List<String> productIds; // List of ordered products
//     private double totalAmount; // Total amount
    // private OrderStatus status; // Use Enum for status
//     private Date createdAt;
//     private Date updatedAt;

//     // Constructor
//     public Order() {}

//     // Getters and Setters
//     public String getId() {
//         return id;
//     }

//     public void setId(String id) {
//         this.id = id;
//     }

//     public String getUserId() {
//         return userId;
//     }

//     public void setUserId(String userId) {
//         this.userId = userId;
//     }

//     public List<String> getProductIds() {
//         return productIds;
//     }

//     public void setProductIds(List<String> productIds) {
//         this.productIds = productIds;
//     }

//     public double getTotalAmount() {
//         return totalAmount;
//     }

//     public void setTotalAmount(double totalAmount) {
//         this.totalAmount = totalAmount;
//     }

    // public OrderStatus getStatus() {
    //     return status;
    // }

    // public void setStatus(OrderStatus status) {
    //     this.status = status;
    // }

    // public Date getCreatedAt() {
    //     return createdAt;
    // }

    // public void setCreatedAt(Date createdAt) {
    //     this.createdAt = createdAt;
    // }

    // public Date getUpdatedAt() {
    //     return updatedAt;
    // }

    // public void setUpdatedAt(Date updatedAt) {
    //     this.updatedAt = updatedAt;
    // }
// }


package com.pocketbazaar.api.model;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List; // To use List

@Document(collection = "orders") // MongoDB collection name
public class Order {

    @Id
    private String id;
    private String userId; // To link orders to specific users (if applicable)
    private List<Product> products; // List of products in the order
    private OrderStatus status; // Use Enum for status
    private double totalAmount; // Total price of the order
    private Date createdAt;
        private Date updatedAt;
    // Constructor
    public Order() {}

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}




package com.pocketbazaar.api.controller;


import com.pocketbazaar.api.model.Product;
import com.pocketbazaar.api.repository.ProductRepository;
import com.pocketbazaar.api.util.CurrentUserSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // 1. Add Product (Custom Path)
    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {

        // String email = CurrentUserSession.getCurrentUserDetails().getUsername();
        String userId = CurrentUserSession.getCurrentUserDetails().getUsername();

        
        // Set the user's email in the product before saving
        product.setUserId(userId);
        
        Product savedProduct = productRepository.save(product);
        return ResponseEntity.ok(savedProduct);
    }

    // 2. Get All Products (Custom Path)
    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return ResponseEntity.ok(products);
    }

    // 3. Get Product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. Update Product (Custom Path)
    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody Product updatedProduct) {
        return productRepository.findById(id).map(product -> {
            product.setName(updatedProduct.getName());
            product.setPrice(updatedProduct.getPrice());
            product.setMedia(updatedProduct.getMedia());
            product.setCategory(updatedProduct.getCategory());
            Product savedProduct = productRepository.save(product);
            return ResponseEntity.ok(savedProduct);
        }).orElse(ResponseEntity.notFound().build());
    }

    // 5. Delete Product (Custom Path)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 6. Get Products for Current User
    @GetMapping("/user/products")
    public ResponseEntity<List<Product>> getProductsForCurrentUser() {
        
        String userId = CurrentUserSession.getCurrentUserDetails().getUsername();
        
        // Fetch products by user email
        List<Product> products = productRepository.findByUserId(userId);
        
        return ResponseEntity.ok(products);
    }
}

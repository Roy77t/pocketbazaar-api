package com.pocketbazaar.api.controller;


import com.pocketbazaar.api.model.Cart;
import com.pocketbazaar.api.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(@RequestParam String userId,
                                          @RequestParam String productId,
                                          @RequestParam int quantity) {
        Cart cart = cartService.addProductToCart(userId, productId, quantity);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Cart> removeFromCart(@RequestParam String userId,
                                               @RequestParam String productId) {
        Cart cart = cartService.removeProductFromCart(userId, productId);
        return ResponseEntity.ok(cart);
    }

    @GetMapping
    public ResponseEntity<Cart> getCart(@RequestParam String userId) {
        Cart cart = cartService.getCart(userId);
        return ResponseEntity.ok(cart);
    }
}


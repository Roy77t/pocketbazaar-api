// package com.pocketbazaar.api.controller;


// import com.pocketbazaar.api.model.Cart;
// import com.pocketbazaar.api.service.CartService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/api/cart")
// public class CartController {

//     @Autowired
//     private CartService cartService;

//     @PostMapping("/add")
//     public ResponseEntity<Cart> addToCart(@RequestParam String userId,
//                                           @RequestParam String productId,
//                                           @RequestParam int quantity) {
//         Cart cart = cartService.addProductToCart(userId, productId, quantity);
//         return ResponseEntity.ok(cart);
//     }

//     @DeleteMapping("/remove")
//     public ResponseEntity<Cart> removeFromCart(@RequestParam String userId,
//                                                @RequestParam String productId) {
//         Cart cart = cartService.removeProductFromCart(userId, productId);
//         return ResponseEntity.ok(cart);
//     }

//     @GetMapping
//     public ResponseEntity<Cart> getCart(@RequestParam String userId) {
//         Cart cart = cartService.getCart(userId);
//         return ResponseEntity.ok(cart);
//     }
// }



package com.pocketbazaar.api.controller;

import com.pocketbazaar.api.model.Cart;
import com.pocketbazaar.api.service.CartService;
import com.pocketbazaar.api.util.CurrentUserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // Add product to the cart
    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(@RequestParam String productId, @RequestParam int quantity) {
        String userId = CurrentUserSession.getCurrentUserDetails().getUsername(); // Get userId from session
        Cart cart = cartService.addProductToCart(userId, productId, quantity);
        return ResponseEntity.ok(cart);
    }

    // Remove product from the cart
    @DeleteMapping("/remove")
    public ResponseEntity<Cart> removeFromCart(@RequestParam String productId) {
        String userId = CurrentUserSession.getCurrentUserDetails().getUsername(); // Get userId from session
        Cart cart = cartService.removeProductFromCart(userId, productId);
        return ResponseEntity.ok(cart);
    }

    // Get the user's cart
    @GetMapping
    public ResponseEntity<Cart> getCart() {
        String userId = CurrentUserSession.getCurrentUserDetails().getUsername(); // Get userId from session
        Cart cart = cartService.getCart(userId);
        return ResponseEntity.ok(cart);
    }
}

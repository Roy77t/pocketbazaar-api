package com.pocketbazaar.api.service;


import com.pocketbazaar.api.model.Cart;
import com.pocketbazaar.api.model.CartItem;
import com.pocketbazaar.api.model.Product;
import com.pocketbazaar.api.repository.CartRepository;
import com.pocketbazaar.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public Cart addProductToCart(String userId, String productId, int quantity) {
        Cart cart = cartRepository.findByUserId(userId).orElse(new Cart());
        cart.setUserId(userId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .orElse(null);

        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setProductId(productId);
            cartItem.setProductName(product.getName());
            cartItem.setPrice(product.getPrice());
            cartItem.setMedia(product.getMedia()); 
            cartItem.setQuantity(quantity);
            cart.getItems().add(cartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        cart.setTotalPrice(cart.getItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum());

        return cartRepository.save(cart);
    }

    public Cart removeProductFromCart(String userId, String productId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cart.getItems().removeIf(item -> item.getProductId().equals(productId));

        cart.setTotalPrice(cart.getItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum());

        return cartRepository.save(cart);
    }

    public Cart getCart(String userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }
}


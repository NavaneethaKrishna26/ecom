package com.ecomapp.controller;

import com.ecomapp.dto.CartItemDTO.CartItemRequest;
import com.ecomapp.model.CartItem;
import com.ecomapp.service.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("ecomapp/cart")
@Transactional
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(@RequestHeader("X-User-Id") long id, @RequestBody CartItemRequest cartrequest) {
        if (cartService.addTocart(id, cartrequest)) {
            return ResponseEntity.ok("Card added success");
        }
        return ResponseEntity.status(400).body("Unable to add to cart");
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteCartItem(@RequestHeader("X-User-Id") long userId, @PathVariable long productId) {
        if (cartService.deleteCartItem(userId, productId)) {
            return ResponseEntity.ok("Delete success");
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getCartItems(@RequestHeader("X-User-Id") long userId) {
        return ResponseEntity.ok(cartService.getCartItems(userId));
    }
}

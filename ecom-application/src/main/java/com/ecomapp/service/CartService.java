package com.ecomapp.service;

import com.ecomapp.dto.CartItemDTO.CartItemRequest;
import com.ecomapp.model.CartItem;
import com.ecomapp.model.Product;
import com.ecomapp.model.User;
import com.ecomapp.repository.CartItemRepository;
import com.ecomapp.repository.ProductRepository;
import com.ecomapp.repository.UserRepository;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public boolean addTocart(long userid, CartItemRequest cartrequest) {
        Optional<Product> optionalProduct = productRepository.findById(cartrequest.getProductId());
        if (optionalProduct.isEmpty()) {
            return false;
        }
        Product product = optionalProduct.get();
        if (product.getStockQuantity() < cartrequest.getQuantity()) {
            return false;
        }
        Optional<User> userOptional = userRepository.findById(userid);
        if (userOptional.isEmpty()) {
            return false;
        }
        User user = userOptional.get();
        CartItem existiongCartItem = cartItemRepository.findByUserAndProduct(user, product);
        if (existiongCartItem != null) {
            existiongCartItem.setQuantity(existiongCartItem.getQuantity() + cartrequest.getQuantity());
            existiongCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existiongCartItem.getQuantity())));
            cartItemRepository.save(existiongCartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(cartrequest.getQuantity())));
            cartItem.setQuantity(cartrequest.getQuantity());
            cartItemRepository.save(cartItem);
        }
        return true;
    }

    public boolean deleteCartItem(long userId, long productId)
    {
        Optional<User> userOptional=userRepository.findById(userId);
        Optional<Product> productOptional=productRepository.findById(productId);
        if(userOptional.isPresent() && productOptional.isPresent()){
            cartItemRepository.deleteByUserAndProduct(userOptional.get(),productOptional.get());
            return true;
        }
        return false;
    }

    public List<CartItem> getCartItems(long userId)
    {
        return cartItemRepository.findByUserId(userId);
    }

    public List<CartItem> getCart(String userId) {

        long id;
        try {
            id = Long.parseLong(userId);
        } catch (Exception e) {
            return List.of();
        }

        return cartItemRepository.findByUserId(id);
    }



    public void clearCart(String userId) {
        userRepository.findById(Long.valueOf(userId)).ifPresent(
                cartItemRepository::deleteByUser
        );
    }
}

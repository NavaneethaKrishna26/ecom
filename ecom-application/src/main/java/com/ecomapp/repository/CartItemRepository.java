package com.ecomapp.repository;

import com.ecomapp.model.CartItem;
import com.ecomapp.model.Product;
import com.ecomapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository <CartItem,Long>{
    CartItem findByUserAndProduct(User user, Product product);

    void deleteByUserAndProduct(User user, Product product);

    List<CartItem> findByUserId(long userId);

    Object findByUser(User user);

    void deleteByUser(User user);
}

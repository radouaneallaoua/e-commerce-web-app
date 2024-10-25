package com.example.e_commerce_app_backend.repository;

import com.example.e_commerce_app_backend.entities.Product;
import com.example.e_commerce_app_backend.entities.ProductReaction;
import com.example.e_commerce_app_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductReactionRepository extends JpaRepository<ProductReaction,Long> {

    @Query("SELECT CASE WHEN COUNT(pr) > 0 THEN true ELSE false END FROM ProductReaction pr WHERE pr.user = :user AND pr.product = :product")
    boolean existsByUserAndProduct(@Param("user") User user, @Param("product") Product product);

    @Query("SELECT COUNT(pr) FROM ProductReaction pr WHERE pr.product=:product")
    int numberOfProductReactions(@Param("product") Product product);

    void deleteProductReactionByUserAndProduct(User user,Product product);
    List<ProductReaction> findAllByUserId(String userId);
}

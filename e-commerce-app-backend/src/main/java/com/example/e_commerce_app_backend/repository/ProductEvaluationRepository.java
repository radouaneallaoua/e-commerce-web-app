package com.example.e_commerce_app_backend.repository;

import com.example.e_commerce_app_backend.entities.Product;
import com.example.e_commerce_app_backend.entities.ProductEvaluation;
import com.example.e_commerce_app_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductEvaluationRepository extends JpaRepository<ProductEvaluation,Long> {
    ProductEvaluation findProductEvaluationByUserIdAndProductId(String userId,Long productId);
    List<ProductEvaluation> findAllByProductId(Long productId);

}

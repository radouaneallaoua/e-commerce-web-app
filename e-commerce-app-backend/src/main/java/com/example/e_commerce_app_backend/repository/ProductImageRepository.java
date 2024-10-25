package com.example.e_commerce_app_backend.repository;

import com.example.e_commerce_app_backend.entities.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage,Long> {
    void deleteProductImageById(Long id);
    List<ProductImage> findProductImagesByProductId(Long productId);
}

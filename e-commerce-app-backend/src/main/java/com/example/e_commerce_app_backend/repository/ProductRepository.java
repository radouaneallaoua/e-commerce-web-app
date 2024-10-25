package com.example.e_commerce_app_backend.repository;

import com.example.e_commerce_app_backend.enums.Color;
import com.example.e_commerce_app_backend.enums.ProductStatus;
import com.example.e_commerce_app_backend.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findAllByNameIsContainingIgnoreCase(String name);
    List<Product> findAllByNameIsContainingIgnoreCaseAndProductStatus(String name,ProductStatus status);
//    List<Product> findAllByCategoryIdAndBrandIdAndColor(Long categoryId, Long brandId, Color color);
//    List<Product> findAllByCategoryIdAndBrandIdAndColorAndPriceLessThanEqual(Long categoryId, Long brandId, Color color,double price);
    List<Product> findAllByCategoryId(Long categoryId);
    List<Product> findAllByProductStatus(ProductStatus productStatus);
    List<Product> findAllByPriceBetween(double min,double max);
    List<Product> findAllByPriceLessThan(double max);
   // List<Product> findAllByCategoryIdAndBrandIdAndColorAndPriceBetween(Long categoryId,Long brand,Color color,double min,double max);
    List<Product> findAllByCategoryIdAndBrandId(Long categoryId, Long brandId);
}

package com.example.e_commerce_app_backend.repository;

import com.example.e_commerce_app_backend.entities.ProductColor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductColorRepository extends JpaRepository<ProductColor,Long> {
}

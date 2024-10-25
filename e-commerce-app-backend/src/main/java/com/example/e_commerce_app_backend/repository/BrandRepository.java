package com.example.e_commerce_app_backend.repository;

import com.example.e_commerce_app_backend.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand,Long> {
    public void deleteBrandById(Long brandId);
}

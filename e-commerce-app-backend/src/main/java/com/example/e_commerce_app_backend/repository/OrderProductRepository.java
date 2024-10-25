package com.example.e_commerce_app_backend.repository;

import com.example.e_commerce_app_backend.entities.OrderProduct;
import com.example.e_commerce_app_backend.entities.OrderProductKey;
import com.example.e_commerce_app_backend.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductKey> {

    List<OrderProduct> findAllByOrderId(String  orderId);
}

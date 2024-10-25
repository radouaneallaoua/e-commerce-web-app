package com.example.e_commerce_app_backend.repository;

import com.example.e_commerce_app_backend.enums.OrderStatus;
import com.example.e_commerce_app_backend.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderRepository extends JpaRepository<Order,String> {
    List<Order> findAllByUserId(String userId);
    List<Order> findAllByUserIdAndOrderStatus(String userId, OrderStatus orderStatus);
    List<Order> findAllByOrderStatus(OrderStatus orderStatus);



}

package com.example.e_commerce_app_backend.service;

import com.example.e_commerce_app_backend.dtos.OrderProductResponseDTO;
import com.example.e_commerce_app_backend.dtos.OrderRequestDTO;
import com.example.e_commerce_app_backend.dtos.OrderResponseDTO;
import com.example.e_commerce_app_backend.enums.OrderStatus;
import com.example.e_commerce_app_backend.entities.*;
import com.example.e_commerce_app_backend.repository.OrderProductRepository;
import com.example.e_commerce_app_backend.repository.OrderRepository;
import com.example.e_commerce_app_backend.repository.ProductRepository;
import com.example.e_commerce_app_backend.repository.UserRepository;
import org.apache.coyote.Response;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class OrderService {
    private ProductRepository productRepository;
    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private OrderProductRepository orderProductRepository;

    public OrderService(ProductRepository productRepository, OrderRepository orderRepository, UserRepository userRepository, OrderProductRepository orderProductRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;

        this.orderProductRepository = orderProductRepository;
    }

    public ResponseEntity<List<OrderResponseDTO>> getAllOrdersForTheUser(String userId){
        List<Order> orders=  orderRepository.findAllByUserId(userId);
        List<OrderResponseDTO> dtoList=new ArrayList<>();
        orders.forEach(o->{
            dtoList.add(o.toDto());
        });
        return  ResponseEntity.ok(dtoList);
    }
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(String orderId,OrderStatus orderStatus){
        Order order=orderRepository.findById(orderId).orElse(null);
        if(order==null){
            throw  new RuntimeException("Order not found");
        }
        order.setOrderStatus(orderStatus);
        return ResponseEntity.ok(orderRepository.save(order).toDto());
    }


    public ResponseEntity<List<OrderResponseDTO>> getAllOrdersWithStatus(OrderStatus status){
        List<Order> orders=  orderRepository.findAllByOrderStatus(status);
        List<OrderResponseDTO> dtoList=new ArrayList<>();
        orders.forEach(o->{
            dtoList.add(o.toDto());
        });
        return  ResponseEntity.ok(dtoList);
    }

    public ResponseEntity<OrderResponseDTO> getOrderById(String id){
        Order order=orderRepository.findById(id).orElse(null);
        return  ResponseEntity.ok(order.toDto());
    }

    public ResponseEntity<List<OrderResponseDTO>> getAllOrders(){
        List<Order> orders=  orderRepository.findAll();
        List<OrderResponseDTO> dtoList=new ArrayList<>();
        orders.forEach(o->{
            dtoList.add(o.toDto());
        });
        return  ResponseEntity.ok(dtoList);
    }
    public ResponseEntity<OrderResponseDTO> saveOrder(OrderRequestDTO orderRequestDTO){

        User user=userRepository.findById(orderRequestDTO.getUserId()).get();
        Order order=Order.builder()
                .id(UUID.randomUUID().toString())
                .orderStatus(OrderStatus.IN_PROGRESS)
                .user(user)
                .build();
        Order  savedOrder=orderRepository.save(order);

        orderRequestDTO.getOrderProducts().forEach(orderProductRequestDTO->{
            OrderProduct orderProduct=OrderProduct.builder()
                    .id(OrderProductKey.builder().orderId(orderProductRequestDTO.getOrderId()).productId(orderProductRequestDTO.getProductId()).build())
                    .product(productRepository.findById(orderProductRequestDTO.getProductId()).get())
                    .order(savedOrder)
                    .quantity(orderProductRequestDTO.getQuantity())
                    .build();

            Product product=productRepository.findById(orderProductRequestDTO.getProductId()).get();
            product.setStock(product.getStock()-orderProductRequestDTO.getQuantity());
            orderProductRepository.save(orderProduct);
        });
        return  ResponseEntity.ok(savedOrder.toDto());
    }
    public ResponseEntity<OrderResponseDTO> updateOrder(String orderId,OrderStatus status){
        Order order=orderRepository.findById(orderId).orElse(null);
        if(order==null){
            throw new RuntimeException();
        }
        order.setOrderStatus(status);
        return ResponseEntity.ok( orderRepository.save(order).toDto());
    }

    public void deleteOrderById(String id){
        orderRepository.deleteById(id);
    }


    public ResponseEntity<List<OrderProductResponseDTO>> getAllOrderProducts(String orderId){
        List<OrderProduct> orderProducts=  orderProductRepository.findAllByOrderId(orderId);
        List<OrderProductResponseDTO> dtoList=new ArrayList<>();
        orderProducts.forEach(o->{
            dtoList.add(o.toDto());
        });
        return  ResponseEntity.ok(dtoList);
    }

    public double getOrderSum(String orderId){
        double sum=0;
        ResponseEntity<List<OrderProductResponseDTO>> orderProducts=getAllOrderProducts(orderId);
        for (OrderProductResponseDTO item:
                Objects.requireNonNull(orderProducts.getBody())) {
            Product product=productRepository.findById(item.getProductId()).get();
            sum+=product.getPrice()*item.getQuantity();
        }
        return sum;

    }




}

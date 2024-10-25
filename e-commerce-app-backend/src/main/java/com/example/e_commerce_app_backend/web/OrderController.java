package com.example.e_commerce_app_backend.web;

import com.example.e_commerce_app_backend.dtos.OrderRequestDTO;
import com.example.e_commerce_app_backend.dtos.OrderResponseDTO;
import com.example.e_commerce_app_backend.entities.Order;
import com.example.e_commerce_app_backend.enums.OrderStatus;
import com.example.e_commerce_app_backend.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class OrderController {
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders(){
        return  orderService.getAllOrders();
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable  String id){
        return  orderService.getOrderById(id);
    }

    @DeleteMapping("/orders/{id}/delete")
    public String deleteOrderById(@PathVariable  String id){
        orderService.deleteOrderById(id);
        return  "Order deleted with id"+id;
    }

    @GetMapping("/orders/{orderId}/sum")
    public double getOrderSum(@PathVariable String orderId){
        return  orderService.getOrderSum(orderId);
    }

    @GetMapping("users/{userId}/orders")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrdersWithUserId(@PathVariable  String userId){
        return  orderService.getAllOrdersForTheUser(userId);
    }

    @GetMapping("orders/withStatus")
    public ResponseEntity<List<OrderResponseDTO>>getAllOrdersWithUserId(@RequestParam OrderStatus status){
        return  orderService.getAllOrdersWithStatus(status);
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<OrderResponseDTO> updateOrderById(@PathVariable String id,@RequestParam OrderStatus newStatus){
        return  orderService.updateOrder(id,newStatus);
    }

    @PostMapping("/users/{userId}/saveOrder")
    public ResponseEntity<OrderResponseDTO>  saveOrder(@RequestBody OrderRequestDTO orderRequestDTO){
        return orderService.saveOrder(orderRequestDTO);
    }

}

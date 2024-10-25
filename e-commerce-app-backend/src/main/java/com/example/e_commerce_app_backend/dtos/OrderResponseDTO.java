package com.example.e_commerce_app_backend.dtos;

import com.example.e_commerce_app_backend.enums.OrderStatus;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDTO {
    private String orderId;
    private OrderStatus orderStatus;
    private String userId;
    private List<OrderProductResponseDTO> orderProducts;

}

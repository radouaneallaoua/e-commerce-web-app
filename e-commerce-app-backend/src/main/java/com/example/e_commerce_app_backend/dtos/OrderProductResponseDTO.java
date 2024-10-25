package com.example.e_commerce_app_backend.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderProductResponseDTO {
    private Long productId;
    private int quantity;
    private String orderId;

}

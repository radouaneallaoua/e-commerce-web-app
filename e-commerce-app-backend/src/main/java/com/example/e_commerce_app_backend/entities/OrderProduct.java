package com.example.e_commerce_app_backend.entities;

import com.example.e_commerce_app_backend.dtos.OrderProductResponseDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderProduct {
    @EmbeddedId
    private  OrderProductKey id;
    private int quantity;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    public OrderProductResponseDTO toDto(){
        return  OrderProductResponseDTO.builder()
                .orderId(order.getId())
                .productId(product.getId())
                .quantity(quantity)
                .build();
    }
}

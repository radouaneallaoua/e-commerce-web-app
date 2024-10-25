package com.example.e_commerce_app_backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductKey implements Serializable {
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "order_id")
    private String orderId;

}

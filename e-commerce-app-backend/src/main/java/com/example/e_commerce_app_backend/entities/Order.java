package com.example.e_commerce_app_backend.entities;

import com.example.e_commerce_app_backend.dtos.OrderProductResponseDTO;
import com.example.e_commerce_app_backend.dtos.OrderResponseDTO;
import com.example.e_commerce_app_backend.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="orders")
@JsonIgnoreProperties({"user"})
public class Order {
    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.REMOVE)
    private User user;

    @OneToMany(mappedBy = "order",fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OrderProduct> orderProduct;

    public OrderResponseDTO toDto(){
        List<OrderProductResponseDTO> dtos=new ArrayList<>();
        if(orderProduct!=null) {
            orderProduct.forEach(op -> {
                dtos.add(op.toDto());
            });
        }
        return  OrderResponseDTO.builder()
                .orderId(id)
                .userId(user.getId())
                .orderStatus(orderStatus)
                .orderProducts(dtos)
                .build();
    }

}

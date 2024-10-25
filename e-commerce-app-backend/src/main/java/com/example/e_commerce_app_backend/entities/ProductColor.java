package com.example.e_commerce_app_backend.entities;

import com.example.e_commerce_app_backend.dtos.ProductColorResponseDTO;
import com.example.e_commerce_app_backend.enums.Color;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class ProductColor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Color color;
    @ManyToOne
    private Product product;

    public ProductColorResponseDTO toDTO() {
        return ProductColorResponseDTO.builder()
                .color(color)
                .id(id)
                .build();
    }
}

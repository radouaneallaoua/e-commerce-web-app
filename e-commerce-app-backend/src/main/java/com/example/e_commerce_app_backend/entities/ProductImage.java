package com.example.e_commerce_app_backend.entities;

import com.example.e_commerce_app_backend.dtos.ProductImageResponseDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageURL;

    @ManyToOne
    private Product product;

    public ProductImageResponseDTO toDTO() {
        return ProductImageResponseDTO.builder()
                .image(imageURL)
                .productId(product.getId())
                .id(id)
                .build();
    }
}

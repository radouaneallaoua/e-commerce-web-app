
package com.example.e_commerce_app_backend.entities;

import com.example.e_commerce_app_backend.dtos.ProductSizeResponseDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor @NoArgsConstructor @Builder @Setter @Getter
public class ProductSize {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sizeLabel;

    @ManyToOne
    private Product product;

    public ProductSizeResponseDTO toDTO() {
        return ProductSizeResponseDTO.builder()
                .sizeLabel(sizeLabel)
                .id(id)
                .build();
    }
}

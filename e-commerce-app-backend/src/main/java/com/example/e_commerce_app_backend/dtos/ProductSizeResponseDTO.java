package com.example.e_commerce_app_backend.dtos;

import jakarta.persistence.Entity;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class ProductSizeResponseDTO {
    private Long id;
    private String sizeLabel;
}

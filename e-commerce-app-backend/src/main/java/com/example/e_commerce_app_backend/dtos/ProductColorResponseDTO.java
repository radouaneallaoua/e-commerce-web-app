package com.example.e_commerce_app_backend.dtos;

import com.example.e_commerce_app_backend.enums.Color;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductColorResponseDTO {
    private Long id;
    private Color color;
}

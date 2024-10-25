package com.example.e_commerce_app_backend.dtos;

import com.example.e_commerce_app_backend.enums.Color;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductColorRequestDTO {
    private Color color;
}

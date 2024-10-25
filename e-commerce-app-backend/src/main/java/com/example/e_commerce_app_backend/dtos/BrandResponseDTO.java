package com.example.e_commerce_app_backend.dtos;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BrandResponseDTO {
    private Long id;
    private String name;
}

package com.example.e_commerce_app_backend.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvaluationResponseDTO {
    private Long id;
    private int stars;
    private String userId;
    private Long productId;
}

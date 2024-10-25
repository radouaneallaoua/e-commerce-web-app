package com.example.e_commerce_app_backend.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReactionRequestDTO {
    private String userId;
    private Long productId;
}

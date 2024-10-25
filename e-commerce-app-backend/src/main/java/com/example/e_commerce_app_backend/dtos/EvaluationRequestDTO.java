package com.example.e_commerce_app_backend.dtos;

import com.example.e_commerce_app_backend.entities.Comment;
import com.example.e_commerce_app_backend.entities.User;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvaluationRequestDTO {
    private int stars;
    private String userId;
    private Long productId;
}

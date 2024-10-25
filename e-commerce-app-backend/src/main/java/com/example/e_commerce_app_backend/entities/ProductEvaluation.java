package com.example.e_commerce_app_backend.entities;

import com.example.e_commerce_app_backend.dtos.EvaluationResponseDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor @NoArgsConstructor
@Table(name = "product_evaluation")
public class ProductEvaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int stars;

    @ManyToOne(cascade = CascadeType.ALL)
    private Product product;

    @ManyToOne
    private User user;

    public EvaluationResponseDTO toDTO(){
        return EvaluationResponseDTO.builder()
                .productId(product.getId())
                .userId(user.getId())
                .stars(stars)
                .id(id)
                .build();
    }
}

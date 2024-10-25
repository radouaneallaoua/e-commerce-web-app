package com.example.e_commerce_app_backend.entities;

import com.example.e_commerce_app_backend.dtos.ReactionResponseDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor @NoArgsConstructor
@Table(name = "product_reaction")
public class ProductReaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Product product;

    @ManyToOne
    private User user;
    public ReactionResponseDTO toDto(){
        return  ReactionResponseDTO.builder()
                .productId(product.getId())
                .id(id)
                .userId(user.getId())
                .build();
    }


}

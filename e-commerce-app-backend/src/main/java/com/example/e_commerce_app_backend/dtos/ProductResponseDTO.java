package com.example.e_commerce_app_backend.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private double price;
    private double oldPrice;
    private int stock;
    private Long categoryId;
    private BrandResponseDTO brand;
    private List<ProductColorResponseDTO> productColorList;
    private List<ProductSizeResponseDTO> productSizeList;
    private List<ProductImageResponseDTO> productImageList;
    private List<EvaluationResponseDTO> evaluations;
    private List<ReactionResponseDTO> reactions;

}

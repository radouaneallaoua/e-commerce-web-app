package com.example.e_commerce_app_backend.entities;

import com.example.e_commerce_app_backend.dtos.*;
import com.example.e_commerce_app_backend.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @ToString @Builder @AllArgsConstructor @NoArgsConstructor

public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String name;
    private String description;
    private double price;
    private double oldPrice;
    private int stock;
    @ManyToOne
    private Brand brand;


    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @ManyToOne()
    private Category category;


    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ProductImage> productImageList;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ProductColor> productColors;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ProductSize> productSizeList;

    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OrderProduct> orderProduct;

    @OneToMany(mappedBy = "product",fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(mappedBy = "product",fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ProductReaction> reactions;

    @OneToMany(mappedBy = "product",fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ProductEvaluation> evaluations;


    public ProductResponseDTO toDTO(){
        List<EvaluationResponseDTO> evaluationResponseDTOS=new ArrayList<>();
        if(evaluations!=null){
            evaluations.forEach(e->{
                evaluationResponseDTOS.add(e.toDTO());
            });

        }
        List<ReactionResponseDTO> reactionResponseDTOS=new ArrayList<>();
        if(reactions!=null){
            reactions.forEach(e->{
                reactionResponseDTOS.add(e.toDto());
            });

        }
        List<ProductColorResponseDTO> productColorResponseDTOS=new ArrayList<>();
        if(productColors!=null){
            productColors.forEach(e->{
                productColorResponseDTOS.add(e.toDTO());
            });

        }
        List<ProductSizeResponseDTO> productSizeResponseDTOS=new ArrayList<>();
        if(productSizeList!=null){
            productSizeList.forEach(e->{
                productSizeResponseDTOS.add(e.toDTO());
            });

        }
        List<ProductImageResponseDTO> productImageResponseDTOS=new ArrayList<>();
        if(productImageList!=null){
            productImageList.forEach(e->{
                productImageResponseDTOS.add(e.toDTO());
            });

        }

        return  ProductResponseDTO.builder()
                .id(id)
                .categoryId(category.getId())
                .productColorList(productColorResponseDTOS)
                .description(description)
                .stock(stock)
                .price(price)
                .oldPrice(oldPrice)
                .brand(brand.toDTO())
                .name(name)
                .productSizeList(productSizeResponseDTOS)
                .productImageList(productImageResponseDTOS)
                .evaluations(evaluationResponseDTOS)
                .reactions(reactionResponseDTOS)
                .build();
    }

}

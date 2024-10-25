package com.example.e_commerce_app_backend.entities;

import com.example.e_commerce_app_backend.dtos.CategoryRequestDTO;
import com.example.e_commerce_app_backend.dtos.CategoryResponseDTO;
import com.example.e_commerce_app_backend.dtos.ProductResponseDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String label;
    private String description;
    private String imageURL;
    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Product> products;


    public CategoryResponseDTO toDto(){
        return CategoryResponseDTO.builder()
                .imageURL(imageURL)
                .label(label)
                .description(description)
                .id(id)
                .build();
    }


}

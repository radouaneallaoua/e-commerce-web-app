package com.example.e_commerce_app_backend.entities;

import com.example.e_commerce_app_backend.dtos.BrandResponseDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "brand")
    private List<Product> productList;

    public BrandResponseDTO toDTO(){
        return BrandResponseDTO.builder()
                .name(name)
                .id(id)
                .build();
    }
}

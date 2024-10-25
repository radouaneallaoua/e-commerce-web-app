package com.example.e_commerce_app_backend.dtos;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDTO {
    private String name;
    private String description;
    private double price;
    private double oldPrice;
    private int stock;
    private Long brandId;
    private Long categoryId;
    private List<MultipartFile> productImageList;
    private List<Long> productSizeList;
    private List<Long> productColorList;

}

package com.example.e_commerce_app_backend.dtos;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageResponseDTO {
    private Long id;
    private String image;
    private Long productId;
}

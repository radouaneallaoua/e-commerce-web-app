package com.example.e_commerce_app_backend.dtos;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDTO {
    private String userId;
    private List<OrderProductRequestDTO> orderProducts;

}

package com.example.e_commerce_app_backend.service;

import com.example.e_commerce_app_backend.dtos.ProductColorRequestDTO;
import com.example.e_commerce_app_backend.dtos.ProductColorResponseDTO;
import com.example.e_commerce_app_backend.entities.ProductColor;
import com.example.e_commerce_app_backend.repository.ProductColorRepository;
import com.example.e_commerce_app_backend.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProductColorService {
    private final ProductColorRepository productColorRepository;
    private final ProductRepository productRepository;

    public ProductColorService(ProductColorRepository productColorRepository, ProductRepository productRepository) {
        this.productColorRepository = productColorRepository;
        this.productRepository = productRepository;
    }
    public ResponseEntity<List<ProductColorResponseDTO>> getColors(){
        List<ProductColorResponseDTO> productColorResponseDTOS=new ArrayList<>();
        List<ProductColor> productColorList=productColorRepository.findAll();
        productColorList.forEach(p->{
            productColorResponseDTOS.add(p.toDTO());
        });
        return ResponseEntity.ok(productColorResponseDTOS);
    }

    public ResponseEntity<ProductColorResponseDTO> getColorById(Long colorId){
       ProductColor productColor=productColorRepository.findById(colorId).get();
        return ResponseEntity.ok(productColor.toDTO());
    }

    public ResponseEntity<String> deleteColorById(Long colorId){
        productColorRepository.deleteById(colorId);
        return ResponseEntity.ok("Color deleted");
    }

    public ResponseEntity<ProductColorResponseDTO> addColor(ProductColorRequestDTO productColorRequestDTO){
        ProductColor productColor=ProductColor.builder()

                .color(productColorRequestDTO.getColor())
                .build();
        ProductColor savedColor = productColorRepository.save(productColor);
        return ResponseEntity.ok(savedColor.toDTO());
    }
    public ResponseEntity<ProductColorResponseDTO> updateColor(Long colorId,ProductColorRequestDTO productColorRequestDTO){
        ProductColor productColor=ProductColor.builder()
                .color(productColorRequestDTO.getColor())
                .build();
        ProductColor savedColor = productColorRepository.save(productColor);
        return ResponseEntity.ok(savedColor.toDTO());
    }
}

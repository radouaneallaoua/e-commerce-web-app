package com.example.e_commerce_app_backend.service;

import com.example.e_commerce_app_backend.dtos.ProductSizeRequestDTO;
import com.example.e_commerce_app_backend.dtos.ProductSizeResponseDTO;
import com.example.e_commerce_app_backend.entities.ProductSize;
import com.example.e_commerce_app_backend.repository.ProductSizeRepository;
import com.example.e_commerce_app_backend.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProductSizeService {
    private final ProductSizeRepository productSizeRepository;
    private final ProductRepository productRepository;

    public ProductSizeService(ProductSizeRepository productSizeRepository, ProductRepository productRepository) {
        this.productSizeRepository = productSizeRepository;
        this.productRepository = productRepository;
    }
    public ResponseEntity<List<ProductSizeResponseDTO>> getSizes(){
        List<ProductSizeResponseDTO> productSizeResponseDTOS=new ArrayList<>();
        List<ProductSize> productSizeList=productSizeRepository.findAll();
        productSizeList.forEach(p->{
            productSizeResponseDTOS.add(p.toDTO());
        });
        return ResponseEntity.ok(productSizeResponseDTOS);
    }

    public ResponseEntity<ProductSizeResponseDTO> getSizeById(Long sizeId){
       ProductSize productSize=productSizeRepository.findById(sizeId).get();
        return ResponseEntity.ok(productSize.toDTO());
    }
    
    public ResponseEntity<String> deleteSizeById(Long sizeId){
        productSizeRepository.deleteById(sizeId);
        return ResponseEntity.ok("Size deleted");
    }

    public ResponseEntity<ProductSizeResponseDTO> addSize(ProductSizeRequestDTO productSizeRequestDTO){
        ProductSize productSize=ProductSize.builder()

                .sizeLabel(productSizeRequestDTO.getSizeLabel())
                .build();
        ProductSize savedSize = productSizeRepository.save(productSize);
        return ResponseEntity.ok(savedSize.toDTO());
    }

    public ResponseEntity<ProductSizeResponseDTO> updateSize(Long sizeId,ProductSizeRequestDTO productSizeRequestDTO){
        ProductSize productSize=ProductSize.builder()
                .sizeLabel(productSizeRequestDTO.getSizeLabel())
                .build();
        ProductSize savedSize = productSizeRepository.save(productSize);
        return ResponseEntity.ok(savedSize.toDTO());
    }
}

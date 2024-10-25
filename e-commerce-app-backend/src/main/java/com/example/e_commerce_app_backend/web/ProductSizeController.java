package com.example.e_commerce_app_backend.web;

import com.example.e_commerce_app_backend.dtos.ProductSizeRequestDTO;
import com.example.e_commerce_app_backend.dtos.ProductSizeResponseDTO;
import com.example.e_commerce_app_backend.service.ProductSizeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sizes")
public class ProductSizeController {
    final private ProductSizeService productSizeService;

    public ProductSizeController(ProductSizeService productSizeService) {
        this.productSizeService = productSizeService;
    }
    @GetMapping("")
    public ResponseEntity<List<ProductSizeResponseDTO>> getSizes(){
        return  productSizeService.getSizes();
    }

    @PostMapping("")
    public ResponseEntity<ProductSizeResponseDTO> addSize(@RequestBody ProductSizeRequestDTO productSizeRequestDTO){
        return  productSizeService.addSize(productSizeRequestDTO);
    }
    @PutMapping("/{sizeId}")
    public ResponseEntity<ProductSizeResponseDTO> updateSize(@PathVariable Long sizeId,@RequestBody ProductSizeRequestDTO productSizeRequestDTO){
        return  productSizeService.updateSize(sizeId,productSizeRequestDTO);
    }
    
    @GetMapping("/{sizeId}")
    public ResponseEntity<ProductSizeResponseDTO> getSizeById(@PathVariable Long sizeId){
        return  productSizeService.getSizeById(sizeId);
    }
    
    @DeleteMapping("/{sizeId}")
    public ResponseEntity<String> deleteSizeById(@PathVariable Long sizeId){
        return  productSizeService.deleteSizeById(sizeId);
    }
}

package com.example.e_commerce_app_backend.web;

import com.example.e_commerce_app_backend.dtos.ProductColorRequestDTO;
import com.example.e_commerce_app_backend.dtos.ProductColorResponseDTO;
import com.example.e_commerce_app_backend.service.ProductColorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/colors")
public class ProductColorController {
    final private ProductColorService productColorService;

    public ProductColorController(ProductColorService productColorService) {
        this.productColorService = productColorService;
    }
    @GetMapping("")
    public ResponseEntity<List<ProductColorResponseDTO>> getColors(){
        return  productColorService.getColors();
    }

    @PostMapping("")
    public ResponseEntity<ProductColorResponseDTO> addColor(@RequestBody ProductColorRequestDTO productColorRequestDTO){
        return  productColorService.addColor(productColorRequestDTO);
    }
    @PutMapping("/{colorId}")
    public ResponseEntity<ProductColorResponseDTO> updateColor(@PathVariable Long colorId,@RequestBody ProductColorRequestDTO productColorRequestDTO){
        return  productColorService.updateColor(colorId,productColorRequestDTO);
    }

    @GetMapping("/{colorId}")
    public ResponseEntity<ProductColorResponseDTO> getColorById(@PathVariable Long colorId){
        return  productColorService.getColorById(colorId);
    }

    @DeleteMapping("/{colorId}")
    public ResponseEntity<String> deleteColorById(@PathVariable Long colorId){
        return  productColorService.deleteColorById(colorId);
    }
}

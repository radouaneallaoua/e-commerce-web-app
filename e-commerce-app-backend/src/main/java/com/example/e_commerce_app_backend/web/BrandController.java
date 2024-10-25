package com.example.e_commerce_app_backend.web;

import com.example.e_commerce_app_backend.dtos.BrandRequestDTO;
import com.example.e_commerce_app_backend.dtos.BrandResponseDTO;
import com.example.e_commerce_app_backend.service.BrandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brands")
public class BrandController {
    private  final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }
    @GetMapping("")
    public ResponseEntity<List<BrandResponseDTO>> getAllBrands(){
        return brandService.getAllBrands();
    }

    @PostMapping("")
    public ResponseEntity<BrandResponseDTO> saveBrand(@RequestBody BrandRequestDTO brandRequestDTO){
        return brandService.addBrand(brandRequestDTO);
    }

    @DeleteMapping("/{brandId}")
    public ResponseEntity<String> deleteBrand(@PathVariable Long brandId){
        return brandService.deleteBrand(brandId);
    }

}

package com.example.e_commerce_app_backend.service;

import com.example.e_commerce_app_backend.Exception.BrandNotFoundException;
import com.example.e_commerce_app_backend.dtos.BrandRequestDTO;
import com.example.e_commerce_app_backend.dtos.BrandResponseDTO;
import com.example.e_commerce_app_backend.entities.Brand;
import com.example.e_commerce_app_backend.repository.BrandRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
@Transactional
public class BrandService {
    final private BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }
    public ResponseEntity<List<BrandResponseDTO>> getAllBrands(){
        List<Brand> brandList=brandRepository.findAll();
        List<BrandResponseDTO> brandResponseDTOS=new ArrayList<>();
        brandList.forEach(b->{
            brandResponseDTOS.add(b.toDTO());
        });
        return  ResponseEntity.ok(brandResponseDTOS);
    }
    public ResponseEntity<BrandResponseDTO> getBrandById(Long brandId){
        Brand brand=brandRepository.findById(brandId).get();
        return  ResponseEntity.ok(brand.toDTO());
    }

    public ResponseEntity<BrandResponseDTO> addBrand(BrandRequestDTO brandRequestDTO){
       Brand brand=Brand.builder()
               .name(brandRequestDTO.getName())
               .build();
       Brand savedBrand=brandRepository.save(brand);
       return  ResponseEntity.ok(savedBrand.toDTO());
    }

    public ResponseEntity<BrandResponseDTO> updateBrand(Long brandId,BrandRequestDTO brandRequestDTO){
     Brand brand=brandRepository.findById(brandId).orElseThrow(()-> new BrandNotFoundException("Brand not found"));
     brand.setName(brandRequestDTO.getName());
     Brand savedBrand=brandRepository.save(brand);
        return  ResponseEntity.ok(savedBrand.toDTO());
    }

    public ResponseEntity<String> deleteBrand(Long brandId){
        brandRepository.deleteBrandById(brandId);
        return ResponseEntity.ok("Brand deleted successfully");
    }
}

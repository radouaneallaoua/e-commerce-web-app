package com.example.e_commerce_app_backend.service;

import com.example.e_commerce_app_backend.Exception.CategoryNotFoundException;
import com.example.e_commerce_app_backend.dtos.CategoryRequestDTO;
import com.example.e_commerce_app_backend.dtos.CategoryResponseDTO;
import com.example.e_commerce_app_backend.entities.Category;
import com.example.e_commerce_app_backend.repository.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@Transactional
public class CategoryService {
    final private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories(){
        List<Category> categories= categoryRepository.findAll();
        List<CategoryResponseDTO> dtoList=new ArrayList<>();
        categories.forEach(c->{
            dtoList.add(c.toDto());
        });
        return  ResponseEntity.ok(dtoList);
    }

    public ResponseEntity<CategoryResponseDTO>  getCategoryById(Long id){
        Category category=categoryRepository.findById(id).orElse(null);
        if(category==null){
            throw new CategoryNotFoundException("category not found");
        }
        return  ResponseEntity.ok(category.toDto());
    }

    public ResponseEntity<CategoryResponseDTO> saveCategory(CategoryRequestDTO categoryRequestDTO) throws IOException {
        Path path=Paths.get(System.getProperty("user.home"),"e-commerce-app","category_images");
        if(!Files.exists(path)){
            Files.createDirectories(path);
        }
        String fileId=UUID.randomUUID().toString();
        String extension= Objects.requireNonNull(categoryRequestDTO.getImageURL().getOriginalFilename()).substring(categoryRequestDTO.getImageURL().getOriginalFilename().lastIndexOf("."));
        Path filePath=Paths.get(System.getProperty("user.home"),"e-commerce-app","category_images",fileId+extension);
        Files.copy(categoryRequestDTO.getImageURL().getInputStream(),filePath);
        Category category=Category.builder()
                .description(categoryRequestDTO.getDescription())
                .label(categoryRequestDTO.getLabel())
                .imageURL(filePath.toUri().toString())
                .build();
        Category savedCategory=  categoryRepository.save(category);
        return  ResponseEntity.ok(savedCategory.toDto());
    }

    public void deleteCategoryById(Long id){
        categoryRepository.deleteById(id);
    }

    public ResponseEntity<CategoryResponseDTO> updateCategory(Long id,CategoryRequestDTO categoryRequestDTO) throws IOException {
        Category category=categoryRepository.findById(id).orElse(null);
        if(category==null){
            throw new RuntimeException("Category not found");
        }
        Path path= Paths.get(System.getProperty("user.home"),"e-commerce-app","category_images");
        if(!Files.exists(path)){
            Files.createDirectories(path);
        }
        String fileId= UUID.randomUUID().toString();
        String fileExtension= Objects.requireNonNull(categoryRequestDTO.getImageURL().getOriginalFilename()).substring(categoryRequestDTO.getImageURL().getOriginalFilename().lastIndexOf("."));
        Path filePath=Paths.get(System.getProperty("user.home"),"e-commerce-app","category_images",fileId+fileExtension);
        Files.copy(categoryRequestDTO.getImageURL().getInputStream(),filePath);
        category.setDescription(categoryRequestDTO.getDescription());
        category.setLabel(categoryRequestDTO.getLabel());
        Files.delete(Paths.get(category.getImageURL()));
        category.setImageURL(filePath.toUri().toString());

        Category savedCategory= categoryRepository.save(category);
        return  ResponseEntity.ok(savedCategory.toDto());
    }



}

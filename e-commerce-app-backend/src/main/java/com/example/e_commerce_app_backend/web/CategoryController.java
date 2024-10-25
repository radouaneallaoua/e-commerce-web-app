package com.example.e_commerce_app_backend.web;

import com.example.e_commerce_app_backend.dtos.CategoryRequestDTO;
import com.example.e_commerce_app_backend.dtos.CategoryResponseDTO;
import com.example.e_commerce_app_backend.entities.Category;
import com.example.e_commerce_app_backend.service.CategoryService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    final private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @GetMapping("")
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategorie(){
        return  categoryService.getAllCategories();
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable  Long categoryId){
        return categoryService.getCategoryById(categoryId);
    }

    @PostMapping(value = "",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CategoryResponseDTO> saveCategory(@RequestParam("label") String label,
                                                            @RequestParam("description") String description,
                                                            @RequestParam("imageURL") MultipartFile imageURL) throws IOException {
        CategoryRequestDTO categoryRequestDTO=CategoryRequestDTO.builder()
                .description(description)
                .imageURL(imageURL)
                .label(label)
                .build();
        return  categoryService.saveCategory(categoryRequestDTO);

    }

    @PutMapping(value = "/{categoryId}",consumes = "multipart/form-data")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable  Long categoryId,@RequestBody CategoryRequestDTO categoryRequestDTO) throws IOException {
        return  categoryService.updateCategory(categoryId,categoryRequestDTO);
    }

    @DeleteMapping("/{categoryId}")
    public void deleteCategoryById(@PathVariable Long categoryId){
         categoryService.deleteCategoryById(categoryId);
    }
}

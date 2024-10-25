package com.example.e_commerce_app_backend.web;

import com.example.e_commerce_app_backend.dtos.*;
import com.example.e_commerce_app_backend.enums.Color;
import com.example.e_commerce_app_backend.enums.ProductStatus;
import com.example.e_commerce_app_backend.service.CommentService;
import com.example.e_commerce_app_backend.service.ProductEvaluationService;
import com.example.e_commerce_app_backend.service.ProductReactionService;
import com.example.e_commerce_app_backend.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ProductReactionService productReactionService;
    private final ProductEvaluationService productEvaluationService;
    private final CommentService commentService;

    public ProductController(ProductService productService, ProductReactionService productReactionService, ProductEvaluationService productEvaluationService, CommentService commentService) {
        this.productService = productService;
        this.productReactionService = productReactionService;
        this.productEvaluationService = productEvaluationService;
        this.commentService = commentService;
    }

    @PostMapping("/addComment")
    public ResponseEntity<CommentResponseDTO> addCommentToProduct(@RequestBody CommentRequestDTO commentRequestDTO
                                                                  ){

        return  commentService.addComment(commentRequestDTO);
    }

    @PostMapping("/addSubComment")
    public ResponseEntity<CommentResponseDTO> addSubCommentToProduct(@RequestBody CommentRequestDTO commentRequestDTO
    ){

        return  commentService.addSubComment(commentRequestDTO);
    }

    @GetMapping("")
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(){
        return  productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id){
        return  productService.getProductById(id);
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<ProductResponseDTO> updateProductStatus(@PathVariable Long id,@RequestBody ProductStatus status){
        return productService.updateProductStatus(id, status);
    }


    @PostMapping(value = "",consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponseDTO> saveProduct(
          @ModelAttribute ProductRequestDTO productRequestDTO
    ) throws IOException {

        return  productService.saveProduct(productRequestDTO);

    }


}

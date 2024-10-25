package com.example.e_commerce_app_backend.service;

import com.example.e_commerce_app_backend.Exception.ProductNotFoundException;
import com.example.e_commerce_app_backend.Exception.UserNotFoundException;
import com.example.e_commerce_app_backend.dtos.EvaluationResponseDTO;
import com.example.e_commerce_app_backend.dtos.UserResponseDto;
import com.example.e_commerce_app_backend.entities.Product;
import com.example.e_commerce_app_backend.entities.ProductEvaluation;
import com.example.e_commerce_app_backend.entities.User;
import com.example.e_commerce_app_backend.repository.ProductEvaluationRepository;
import com.example.e_commerce_app_backend.repository.ProductRepository;
import com.example.e_commerce_app_backend.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductEvaluationService {
    private final ProductEvaluationRepository productEvaluationRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private ProductRepository productRepository;


    public ProductEvaluationService(ProductEvaluationRepository productEvaluationRepository, UserService userService, UserRepository userRepository, ProductRepository productRepository) {
        this.productEvaluationRepository = productEvaluationRepository;
        this.userService = userService;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public ResponseEntity<EvaluationResponseDTO> addProductEvaluationFortHeUser(String userId, Long productId, int stars){
        ResponseEntity<UserResponseDto> user=userService.getUserById(userId);
        Product product=productRepository.findById(productId).orElse(null);
        if(product==null){
            throw new ProductNotFoundException("product not found with id"+productId);
        }
        ProductEvaluation productEvaluation=ProductEvaluation.builder()
                .product(product)
                .user(userRepository.findById(user.getBody().getId()).get())
                .stars(stars)
                .build();
        return  ResponseEntity.ok(productEvaluationRepository.save(productEvaluation).toDTO());
    }

    public ResponseEntity<EvaluationResponseDTO> updateProductEvaluationForTheUser(Long evaluationId,int stars){
        ProductEvaluation productEvaluation=productEvaluationRepository.findById(evaluationId).get();
        productEvaluation.setStars(stars);
        return  ResponseEntity.ok(productEvaluationRepository.save(productEvaluation).toDTO());
    }

    public ResponseEntity<EvaluationResponseDTO> getEvaluationWithUserIdAndProductId(String userId, Long productId){
        return  ResponseEntity.ok(productEvaluationRepository.findProductEvaluationByUserIdAndProductId(userId,productId).toDTO());
    }

    public int getTotalStarsForTheProductId(Long productId){
        Product product=productRepository.findById(productId).orElse(null);
        if(product==null){
            throw new ProductNotFoundException("product not found with id"+productId);
        }
        int sum=0;
        List<ProductEvaluation> evaluations=productEvaluationRepository.findAllByProductId(productId);
        for (ProductEvaluation evaluation:
             evaluations) {
            sum+=evaluation.getStars();
        }
        return sum;
    }
}

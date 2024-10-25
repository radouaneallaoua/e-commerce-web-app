package com.example.e_commerce_app_backend.service;

import com.example.e_commerce_app_backend.Exception.ProductNotFoundException;
import com.example.e_commerce_app_backend.Exception.UserNotFoundException;
import com.example.e_commerce_app_backend.dtos.ProductResponseDTO;
import com.example.e_commerce_app_backend.dtos.ReactionRequestDTO;
import com.example.e_commerce_app_backend.dtos.ReactionResponseDTO;
import com.example.e_commerce_app_backend.dtos.UserResponseDto;
import com.example.e_commerce_app_backend.entities.Product;
import com.example.e_commerce_app_backend.entities.ProductReaction;
import com.example.e_commerce_app_backend.entities.User;
import com.example.e_commerce_app_backend.repository.ProductReactionRepository;
import com.example.e_commerce_app_backend.repository.ProductRepository;
import com.example.e_commerce_app_backend.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProductReactionService {
    private final ProductReactionRepository productReactionRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductReactionService(ProductReactionRepository productReactionRepository, ProductService productService, ProductRepository productRepository, UserRepository userRepository, UserService userService) {
        this.productReactionRepository = productReactionRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<ReactionResponseDTO> addProductReaction(ReactionRequestDTO reactionRequestDTO){
        User user=userRepository.findById(reactionRequestDTO.getUserId()).orElse(null);
        if(user==null){
            throw new UserNotFoundException("user not found with id"+reactionRequestDTO.getUserId());
        }
        Product product=productRepository.findById(reactionRequestDTO.getProductId()).orElse(null);
        if(product==null){
            throw new ProductNotFoundException("product not found with id"+reactionRequestDTO.getProductId());
        }
        ProductReaction productReaction=ProductReaction.builder()
                .product(product)
                .user(user)
                .build();
        return ResponseEntity.ok( productReactionRepository.save(productReaction).toDto());
    }

    public Boolean userReactsToTheProduct(String userId,Long productId){
        User user=userRepository.findById(userId).orElse(null);
        if(user==null){
            throw new UserNotFoundException("user not found with id"+userId);
        }
        Product product=productRepository.findById(productId).orElse(null);
        if(product==null){
            throw new ProductNotFoundException("product not found with id"+productId);
        }
        return  productReactionRepository.existsByUserAndProduct(user,product);
    }

    public String deleteProductReactionUserProductWithUserIdAndProductId(String userId,Long  productId){
        User user=userRepository.findById(userId).orElse(null);
        if(user==null){
            throw new UserNotFoundException("user not found with id"+userId);
        }
        Product product=productRepository.findById(productId).orElse(null);
        if(product==null){
            throw new ProductNotFoundException("product not found with id"+productId);
        }
        productReactionRepository.deleteProductReactionByUserAndProduct(user,product);
        return  "reaction deleted for the user "+user.getUserName()+"on "+product.getName();
    }

   public int numberOfProductReactionsWithProductId(Long productId){
       Product product=productRepository.findById(productId).orElse(null);
       if(product==null){
           throw new ProductNotFoundException("product not found with id"+productId);
       }
        return  productReactionRepository.numberOfProductReactions(product);
   }


    public ResponseEntity<List<ReactionResponseDTO>> getAllReactedProductsForTheUser(String userId ){
       List<ProductReaction> reactions=productReactionRepository.findAllByUserId(userId);
       List<ReactionResponseDTO> responseDTOS=new ArrayList<>();
       if(reactions!=null) {
           reactions.forEach(r -> {
               responseDTOS.add(r.toDto());
           });
       }
        return ResponseEntity.ok(responseDTOS);
    }
}

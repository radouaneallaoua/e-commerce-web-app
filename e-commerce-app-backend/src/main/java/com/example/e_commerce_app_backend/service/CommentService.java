package com.example.e_commerce_app_backend.service;

import com.example.e_commerce_app_backend.dtos.CommentRequestDTO;
import com.example.e_commerce_app_backend.dtos.CommentResponseDTO;
import com.example.e_commerce_app_backend.entities.*;
import com.example.e_commerce_app_backend.repository.CommentRepository;
import com.example.e_commerce_app_backend.repository.ProductRepository;
import com.example.e_commerce_app_backend.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CommentService {
    final private CommentRepository commentRepository;
    final private UserRepository userRepository;
    final private ProductRepository productRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }


    public ResponseEntity<List<CommentResponseDTO>> getAllComments(){
        List<Comment> comments=commentRepository.findAll();
        List<CommentResponseDTO> dtoList=new ArrayList<>();
        comments.forEach(c->{
            dtoList.add(c.toDTO());
        });
        return  ResponseEntity.ok(dtoList);
    }

    public ResponseEntity<CommentResponseDTO>  getCommentById(Long id) throws Exception{
        Comment comment=  commentRepository.findById(id).orElse(null);
        if(comment!=null){
            return  ResponseEntity.ok(comment.toDTO());
        }
        throw new RuntimeException("comment not found");

    }

    public ResponseEntity<List<CommentResponseDTO>>  getAllCommentsForTheUserOnTheProduct(String userId, Long productId){
        List<Comment> comments=commentRepository.findAllByUserIdAndProductId(userId,productId);
        List<CommentResponseDTO> dtoList=new ArrayList<>();
        comments.forEach(c->{
            dtoList.add(c.toDTO());
        });
        return  ResponseEntity.ok(dtoList);
    }
    public ResponseEntity<List<CommentResponseDTO>>  getAllCommentsOnTheProduct(Long productId){
        List<Comment> comments=commentRepository.findAllByProductId(productId);
        List<CommentResponseDTO> dtoList=new ArrayList<>();
        comments.forEach(c->{
            dtoList.add(c.toDTO());
        });
        return  ResponseEntity.ok(dtoList);
    }

    public ResponseEntity<CommentResponseDTO> addSubComment(CommentRequestDTO commentRequestDTO){
        User user=userRepository.findById(commentRequestDTO.getUserId()).get();
        Product product=productRepository.findById(commentRequestDTO.getProductId()).get();
        Comment parentComment=commentRepository.findById(commentRequestDTO.getParentCommentId()).get();
        Comment comment=Comment.builder()
                .content(commentRequestDTO.getContent())
                .publicationDate(LocalDateTime.now())
                .user(user)
                .product(product)
                .parentComment(parentComment)
                .build();
        return  ResponseEntity.ok(commentRepository.save(comment).toDTO());
    }
    public ResponseEntity<CommentResponseDTO> addComment(CommentRequestDTO commentRequestDTO){
        User user=userRepository.findById(commentRequestDTO.getUserId()).get();
        Product product=productRepository.findById(commentRequestDTO.getProductId()).get();
        Comment comment=Comment.builder()
                .content(commentRequestDTO.getContent())
                .publicationDate(LocalDateTime.now())
                .user(user)
                .product(product)
                .build();
        return  ResponseEntity.ok(commentRepository.save(comment).toDTO());
    }

    public void deleteCommentById(Long commentId){
        commentRepository.deleteById(commentId);
    }
    public ResponseEntity<List<CommentResponseDTO>> getAllCommentsOnProductWithDateAfter(Long productId,LocalDateTime date){
        List<Comment> comments=commentRepository.findAllByProductIdAndPublicationDateAfter(productId,date);
        List<CommentResponseDTO> dtoList=new ArrayList<>();
        comments.forEach(c->{
            dtoList.add(c.toDTO());
        });
        return  ResponseEntity.ok(dtoList);
    }

    public ResponseEntity<List<CommentResponseDTO>>getAllCommentsOnProductWithDateBefore(Long productId,LocalDateTime date){
        List<Comment> comments=commentRepository.findAllByProductIdAndPublicationDateBefore(productId,date);
        List<CommentResponseDTO> dtoList=new ArrayList<>();
        comments.forEach(c->{
            dtoList.add(c.toDTO());
        });
        return  ResponseEntity.ok(dtoList);
    }
}

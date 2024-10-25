package com.example.e_commerce_app_backend.web;

import com.example.e_commerce_app_backend.dtos.CommentRequestDTO;
import com.example.e_commerce_app_backend.dtos.CommentResponseDTO;
import com.example.e_commerce_app_backend.entities.Comment;
import com.example.e_commerce_app_backend.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @GetMapping("/products/{productId}/comments")

    public ResponseEntity<List<CommentResponseDTO>> getAllProductComments(@PathVariable Long productId){
        return commentService.getAllCommentsOnTheProduct(productId);
    }

    @GetMapping("/products/comments")
    public ResponseEntity<List<CommentResponseDTO>> getAllComments(){
        return commentService.getAllComments();
    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponseDTO> getCommentById(@PathVariable Long commentId) throws Exception {
        return  commentService.getCommentById(commentId);
    }



    @DeleteMapping("/comments/{commentId}/delete")
    public String deleteCommentById(@PathVariable Long commentId){
        commentService.deleteCommentById(commentId);
        return  "comment deleted with id "+commentId;
    }

    @GetMapping("/products/{productId}/comments/{beforedate}")
    public ResponseEntity<List<CommentResponseDTO>> getAllCommentOnProductBeforeTheDate(@PathVariable Long productId,@PathVariable LocalDateTime beforedate){
        return  commentService.getAllCommentsOnProductWithDateBefore(productId,beforedate);
    }

    @GetMapping("/products/{productId}/comments/{afterdate}")
    public ResponseEntity<List<CommentResponseDTO>> getAllCommentOnProductAfterTheDate(@PathVariable Long productId,@PathVariable LocalDateTime afterdate){
        return  commentService.getAllCommentsOnProductWithDateAfter(productId,afterdate);
    }

    @GetMapping("/products/{productId}/comments/{userId}")
    public ResponseEntity<List<CommentResponseDTO>> getAllCommentOnProductForTheUser(@PathVariable Long productId,@PathVariable String userId){
        return  commentService.getAllCommentsForTheUserOnTheProduct(userId,productId);
    }


}

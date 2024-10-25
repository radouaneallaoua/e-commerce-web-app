package com.example.e_commerce_app_backend.repository;

import com.example.e_commerce_app_backend.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByUserIdAndProductId(String userId,Long productId);
    List<Comment> findAllByProductId(Long productId);
    List<Comment> findAllByProductIdAndPublicationDateAfter(Long product_id, LocalDateTime publicationDate);
    List<Comment> findAllByProductIdAndPublicationDateBefore(Long product_id, LocalDateTime publicationDate);

}

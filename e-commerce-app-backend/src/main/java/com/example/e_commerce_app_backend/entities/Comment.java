package com.example.e_commerce_app_backend.entities;

import com.example.e_commerce_app_backend.dtos.CommentResponseDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.example.e_commerce_app_backend.dtos.CommentRequestDTO;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String content;
    private LocalDateTime publicationDate;

    @ManyToOne
    private User user;

    @ManyToOne
    private Product product;

    @ManyToOne
    private  Comment parentComment;

    @OneToMany(mappedBy = "parentComment",fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Comment>  childcomments;

    public CommentResponseDTO toDTO() {
        List<Long> childCommentsIds =new ArrayList<>();
        if(childcomments!=null){
            childcomments.forEach(c->{
                childCommentsIds.add(c.getId());
            });
        }
        return CommentResponseDTO.builder()
                .childCommentIds(childCommentsIds)
                .content(content)
                .id(id)
                .parentCommentId(parentComment.getId())
                .productId(product.getId())
                .userId(user.getId())
                .publicationDate(publicationDate)
                .build();
    }
}

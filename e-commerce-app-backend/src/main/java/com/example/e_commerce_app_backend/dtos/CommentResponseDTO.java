package com.example.e_commerce_app_backend.dtos;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseDTO {
    private Long id;
    private String content;
    private LocalDateTime publicationDate;
    private Long productId;
    private String userId;
    private Long parentCommentId;
    private List<Long> childCommentIds;

}

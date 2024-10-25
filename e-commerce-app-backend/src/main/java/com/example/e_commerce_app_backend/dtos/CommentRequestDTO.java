package com.example.e_commerce_app_backend.dtos;

import com.example.e_commerce_app_backend.entities.User;
import jakarta.annotation.Nullable;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRequestDTO {
    private String content;
    private Long productId;
    private String userId;
    @Nullable()
    private Long parentCommentId;

}

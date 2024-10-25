package com.example.e_commerce_app_backend.dtos;

import com.example.e_commerce_app_backend.enums.NotificationStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponseDTO {
  private Long id;
  private String title;
  private String content;
  private String userId;
  private NotificationStatus status;
  private LocalDateTime date;

}

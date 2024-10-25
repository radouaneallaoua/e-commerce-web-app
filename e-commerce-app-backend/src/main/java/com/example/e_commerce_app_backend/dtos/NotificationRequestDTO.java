package com.example.e_commerce_app_backend.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequestDTO {
  private String title;
  private String content;
  private String userId;

}

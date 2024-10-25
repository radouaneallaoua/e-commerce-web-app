package com.example.e_commerce_app_backend.entities;

import com.example.e_commerce_app_backend.dtos.NotificationResponseDTO;
import com.example.e_commerce_app_backend.dtos.OrderProductResponseDTO;
import com.example.e_commerce_app_backend.dtos.OrderResponseDTO;
import com.example.e_commerce_app_backend.enums.NotificationStatus;
import com.example.e_commerce_app_backend.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Notification {
    @Id
    private Long id;
    private String content;
    private String title;
    private LocalDateTime date;
    @Enumerated(EnumType.STRING)
    private NotificationStatus notificationStatus;

    @ManyToOne()
    private User user;

    public NotificationResponseDTO toDto(){
        return  NotificationResponseDTO.builder()
                .userId(user.getId())
                .content(content)
                .date(date)
                .status(notificationStatus)
                .title(title)
                .id(id)
                .build();
    }

}

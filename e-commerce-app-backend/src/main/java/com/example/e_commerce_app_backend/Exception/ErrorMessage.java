package com.example.e_commerce_app_backend.Exception;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
    private String message;
    private LocalDateTime timestamp;
    private int code;
}

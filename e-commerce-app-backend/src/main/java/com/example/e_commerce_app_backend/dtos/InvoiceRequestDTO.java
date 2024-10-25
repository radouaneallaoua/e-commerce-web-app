package com.example.e_commerce_app_backend.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceRequestDTO {
    private double sum;
    private LocalDateTime paymentDate;
    private String orderId;
}

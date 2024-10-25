package com.example.e_commerce_app_backend.entities;

import com.example.e_commerce_app_backend.dtos.InvoiceResponseDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double sum;
    private LocalDateTime paymentDate;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Order order;

    public InvoiceResponseDTO toDto(){
        return  InvoiceResponseDTO.builder()
                .orderId(order.getId())
                .paymentDate(paymentDate)
                .sum(sum)
                .build();
    }


}

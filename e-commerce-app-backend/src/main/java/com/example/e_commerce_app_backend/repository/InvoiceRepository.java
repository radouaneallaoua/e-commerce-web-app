package com.example.e_commerce_app_backend.repository;

import com.example.e_commerce_app_backend.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Long>{
    List<Invoice> findAllByPaymentDateBefore(LocalDateTime paymentDate);
    List<Invoice> findAllByPaymentDateAfter(LocalDateTime paymentDate);
    Invoice findInvoiceByOrderId(String orderId);
    Invoice findInvoiceByOrderIdAndPaymentDateBetween(String order_id, LocalDateTime paymentDate, LocalDateTime paymentDate2);
    List<Invoice> findAllByPaymentDateIsBetween(LocalDateTime paymentDate, LocalDateTime paymentDate2);

}

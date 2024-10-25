package com.example.e_commerce_app_backend.service;

import com.example.e_commerce_app_backend.dtos.InvoiceRequestDTO;
import com.example.e_commerce_app_backend.dtos.InvoiceResponseDTO;
import com.example.e_commerce_app_backend.entities.Invoice;
import com.example.e_commerce_app_backend.entities.Order;
import com.example.e_commerce_app_backend.enums.OrderStatus;
import com.example.e_commerce_app_backend.repository.InvoiceRepository;
import com.example.e_commerce_app_backend.repository.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class InvoiceService {
    final private InvoiceRepository invoiceRepository;
    private final OrderRepository orderRepository;

    public InvoiceService(InvoiceRepository invoiceRepository, OrderRepository orderRepository) {
        this.invoiceRepository = invoiceRepository;
        this.orderRepository = orderRepository;
    }
    public ResponseEntity<List<InvoiceResponseDTO>> getAllInvoices(){
        List<Invoice> invoices=  invoiceRepository.findAll();
        List<InvoiceResponseDTO> dtoList=new ArrayList<>();
        invoices.forEach(i->{
            dtoList.add(i.toDto());
        });
        return  ResponseEntity.ok(dtoList);
    }

    public ResponseEntity<List<InvoiceResponseDTO>> getAllUserInvoices(String userId){
        List<Invoice> userInvoices=new ArrayList<>();
        List<Order> orders=orderRepository.findAllByUserId(userId);
        for (Order order:
                orders) {
            Invoice invoice=invoiceRepository.findInvoiceByOrderId(order.getId());
            userInvoices.add(invoice);

        }
        List<InvoiceResponseDTO> dtoList=new ArrayList<>();
        userInvoices.forEach(i->{
            dtoList.add(i.toDto());
        });
        return  ResponseEntity.ok(dtoList);
    }
    public ResponseEntity<List<InvoiceResponseDTO>> getAllUserInvoicesDateBetween(String userId,LocalDateTime start,LocalDateTime end){
        List<Invoice> userInvoices=new ArrayList<>();
        List<Order> orders=orderRepository.findAllByUserId(userId);
        for (Order order:
                orders) {
            Invoice invoice=invoiceRepository.findInvoiceByOrderIdAndPaymentDateBetween(order.getId(),start,end);
            userInvoices.add(invoice);
        }
        List<InvoiceResponseDTO> dtoList=new ArrayList<>();
        userInvoices.forEach(i->{
            dtoList.add(i.toDto());
        });
        return  ResponseEntity.ok(dtoList);

    }
    public ResponseEntity<InvoiceResponseDTO> getInvoiceById(Long id){
        Invoice invoice=invoiceRepository.findById(id).orElse(null);
        return  ResponseEntity.ok(invoice.toDto());
    }
    public ResponseEntity<InvoiceResponseDTO> saveInvoice(InvoiceRequestDTO invoiceRequestDTO){
        Order order=orderRepository.findById(invoiceRequestDTO.getOrderId()).get();
        Invoice invoice=Invoice.builder()
                .order(order)
                .sum(invoiceRequestDTO.getSum())
                .paymentDate(LocalDateTime.now())
                .build();
        order.setOrderStatus(OrderStatus.PAYED);
        return  ResponseEntity.ok(invoiceRepository.save(invoice).toDto());
    }

    public ResponseEntity<List<InvoiceResponseDTO>> getAllInvoicesWithDateBetween(LocalDateTime date1,LocalDateTime date2){
        List<Invoice> invoices=  invoiceRepository.findAllByPaymentDateIsBetween(date1,date2);
        List<InvoiceResponseDTO> dtoList=new ArrayList<>();
        invoices.forEach(i->{
            dtoList.add(i.toDto());
        });
        return  ResponseEntity.ok(dtoList);
    }

    public ResponseEntity<List<InvoiceResponseDTO>> getAllInvoicesWithDateAfter(LocalDateTime date){
        List<Invoice> invoices=  invoiceRepository.findAllByPaymentDateAfter(date);
        List<InvoiceResponseDTO> dtoList=new ArrayList<>();
        invoices.forEach(i->{
            dtoList.add(i.toDto());
        });
        return  ResponseEntity.ok(dtoList);
    }
    public ResponseEntity<List<InvoiceResponseDTO>> getAllInvoicesWithDateBefore(LocalDateTime date){
        List<Invoice> invoices=  invoiceRepository.findAllByPaymentDateBefore(date);
        List<InvoiceResponseDTO> dtoList=new ArrayList<>();
        invoices.forEach(i->{
            dtoList.add(i.toDto());
        });
        return  ResponseEntity.ok(dtoList);
    }

}

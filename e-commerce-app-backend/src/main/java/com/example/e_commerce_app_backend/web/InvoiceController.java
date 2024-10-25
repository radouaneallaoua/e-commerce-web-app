package com.example.e_commerce_app_backend.web;

import com.example.e_commerce_app_backend.dtos.InvoiceRequestDTO;
import com.example.e_commerce_app_backend.dtos.InvoiceResponseDTO;
import com.example.e_commerce_app_backend.entities.Invoice;
import com.example.e_commerce_app_backend.entities.Order;
import com.example.e_commerce_app_backend.service.InvoiceService;
import com.example.e_commerce_app_backend.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class InvoiceController {
    private InvoiceService invoiceService;
    private OrderService orderService;

    public InvoiceController(InvoiceService invoiceService, OrderService orderService) {
        this.invoiceService = invoiceService;
        this.orderService = orderService;
    }

    @GetMapping("/invoices/{invoiceId}")
    public ResponseEntity<InvoiceResponseDTO> getInvoiceById(@PathVariable Long invoiceId){
        return  invoiceService.getInvoiceById(invoiceId);
    }

    @GetMapping("/invoices/")
    public ResponseEntity<List<InvoiceResponseDTO>> getAllInvoices(){
        return  invoiceService.getAllInvoices();
    }

    @PostMapping("orders/makepayment")
    public ResponseEntity<InvoiceResponseDTO> addInvoice(@RequestBody InvoiceRequestDTO invoiceRequestDTO){
        return  invoiceService.saveInvoice(invoiceRequestDTO);
    }

    @GetMapping("/invoices/afterDate/")
    public ResponseEntity<List<InvoiceResponseDTO>> getAllInvoicesAfterDate(@RequestParam LocalDateTime date){
        return  invoiceService.getAllInvoicesWithDateAfter(date);
    }

    @GetMapping("/invoices/beforeDate/")
    public ResponseEntity<List<InvoiceResponseDTO>> getAllInvoicesBeforeDate(@RequestParam LocalDateTime date){
        return  invoiceService.getAllInvoicesWithDateBefore(date);
    }

    @GetMapping("/invoices/betweenDate/")
    public ResponseEntity<List<InvoiceResponseDTO>> getAllInvoicesBetweenDates(@RequestParam("date1") LocalDateTime date1,@RequestParam("date2") LocalDateTime date2){
        return  invoiceService.getAllInvoicesWithDateBetween(date1,date2);
    }

    @GetMapping("/users/{userId}/invoices/")
    public ResponseEntity<List<InvoiceResponseDTO>> getAllInvoicesForTheUserBetweenDates(@PathVariable String userId,@RequestParam("start") LocalDateTime start,@RequestParam("end") LocalDateTime end){
        return  invoiceService.getAllUserInvoicesDateBetween(userId,start,end);
    }


}

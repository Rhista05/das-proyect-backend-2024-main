package com.example.backend.Controller.Controllers;

import com.example.backend.Controller.Services.EmailService;
import com.example.backend.Controller.Services.PaymentService;
import com.example.backend.Controller.Services.SaleService;
import com.example.backend.Model.Entities.Payment;
import com.example.backend.Model.Entities.Sale;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final SaleService saleService; // Asegúrate de tener un servicio para manejar las ventas
    private final EmailService emailService;

    public PaymentController(PaymentService paymentService, SaleService saleService, EmailService emailService) {
        this.paymentService = paymentService;
        this.saleService = saleService;
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {

        // Verificar si la venta existe
        Long saleId = payment.getSale().getId();
        Sale sale = saleService.findSaleById(saleId)
                .orElseThrow(() -> new RuntimeException("Sale not found"));

        // Establecer la relación en Payment
        payment.setSale(sale);

        // Guardar el pago
        Payment createdPayment = paymentService.savePayment(payment);

        // Preparar los detalles del correo
        String saleDetails = "Sale ID: " + sale.getId() + "\n"
                + "Client: " + sale.getClient() + "\n"
                + "Total: $" + payment.getAmount();

        // Enviar correo de confirmación (a través del patrón Factory)
        emailService.sendPaymentConfirmationEmail(sale.getEmail(), sale.getClient(), saleDetails);

        return new ResponseEntity<>(createdPayment, HttpStatus.CREATED);
    }

    @GetMapping("/sale/{saleId}")
    public ResponseEntity<Boolean> hasPayment(@PathVariable Long saleId) {
        boolean exists = paymentService.existsBySaleId(saleId);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }
}

package com.example.backend.Controller.Services;

import com.example.backend.Model.Entities.Payment;
import com.example.backend.Model.Repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public boolean existsBySaleId(Long saleId) {
        return paymentRepository.existsBySale_Id(saleId); 
    }
}

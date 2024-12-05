package com.example.backend.Model.Repositories;

import com.example.backend.Model.Entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    boolean existsBySale_Id(Long saleId);
}

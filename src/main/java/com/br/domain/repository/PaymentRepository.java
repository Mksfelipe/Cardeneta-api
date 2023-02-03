package com.br.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.domain.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{

}

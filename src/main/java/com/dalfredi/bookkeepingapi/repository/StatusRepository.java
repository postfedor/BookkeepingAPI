package com.dalfredi.bookkeepingapi.repository;

import com.dalfredi.bookkeepingapi.model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<PaymentStatus, Long> {
}

package com.dalfredi.bookkeepingapi.repository;

import com.dalfredi.bookkeepingapi.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}

package com.dalfredi.bookkeepingapi.repository;

import com.dalfredi.bookkeepingapi.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}

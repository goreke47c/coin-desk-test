package com.example.repo;

import com.example.entity.CurrencyMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyMappingRepository extends JpaRepository<CurrencyMapping, String> {
    // You can define custom query methods if needed
}

package com.example.msbalanceinquiriservice.repository;

import com.example.msbalanceinquiriservice.model.Commission;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface CommissionRepository extends ReactiveMongoRepository<Commission, String> {
    Flux<Commission> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    Flux<Commission> findByProductIdAndDateBetween(String productId, LocalDateTime startDate, LocalDateTime endDate);
    Flux<Commission> findByProductTypeAndDateBetween(String productType, LocalDateTime startDate, LocalDateTime endDate);
}


package com.example.msbalanceinquiriservice.repository;

import com.example.msbalanceinquiriservice.model.DailyBalance;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

public interface DailyBalanceRepository extends ReactiveMongoRepository<DailyBalance, String> {
    Flux<DailyBalance> findByCustomerIdAndDateBetween(String customerId, LocalDate startDate, LocalDate endDate);
    Flux<DailyBalance> findByCustomerIdAndProductIdAndDateBetween(String clientId, String productId, LocalDate startDate, LocalDate endDate);
}
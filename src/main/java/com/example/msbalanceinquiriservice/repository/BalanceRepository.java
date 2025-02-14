package com.example.msbalanceinquiriservice.repository;

import com.example.msbalanceinquiriservice.model.Balance;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface BalanceRepository extends ReactiveMongoRepository<Balance, String> {
    Flux<Balance> findByClientId(String clientId);
    Flux<Balance> findByClientIdAndProductType(String clientId, String productType);
}

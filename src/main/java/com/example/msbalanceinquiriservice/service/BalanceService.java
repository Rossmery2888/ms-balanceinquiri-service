package com.example.msbalanceinquiriservice.service;

import com.example.msbalanceinquiriservice.dto.BalanceResponse;
import com.example.msbalanceinquiriservice.dto.ConsolidatedProductsResponse;

import reactor.core.publisher.Mono;


public interface BalanceService {
    Mono<BalanceResponse> getBalances(String accountId, String creditCardId);
    Mono<ConsolidatedProductsResponse> getConsolidatedProducts(String customerId);
}
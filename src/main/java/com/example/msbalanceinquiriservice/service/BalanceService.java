package com.example.msbalanceinquiriservice.service;

import com.example.msbalanceinquiriservice.dto.BalanceResponse;
import com.example.msbalanceinquiriservice.dto.CommissionReportResponse;
import com.example.msbalanceinquiriservice.dto.ConsolidatedProductsResponse;

import com.example.msbalanceinquiriservice.dto.MonthlyAverageBalanceResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDate;


public interface BalanceService {
    Mono<BalanceResponse> getBalances(String accountId, String creditCardId);
    Mono<ConsolidatedProductsResponse> getConsolidatedProducts(String customerId);
    Mono<MonthlyAverageBalanceResponse> getMonthlyAverageBalance(String customerId);
    Mono<CommissionReportResponse> getCommissionReport(LocalDate startDate, LocalDate endDate, String productType);
}

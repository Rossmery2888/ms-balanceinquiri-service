package com.example.msbalanceinquiriservice.controller;


import com.example.msbalanceinquiriservice.dto.BalanceResponse;
import com.example.msbalanceinquiriservice.dto.CommissionReportResponse;
import com.example.msbalanceinquiriservice.dto.ConsolidatedProductsResponse;
import com.example.msbalanceinquiriservice.dto.MonthlyAverageBalanceResponse;
import com.example.msbalanceinquiriservice.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/balances")
@RequiredArgsConstructor
public class BalanceController {
    private final BalanceService balanceService;

    @GetMapping("/inquiry")
    public Mono<BalanceResponse> getBalances(
            @RequestParam String accountId,
            @RequestParam String creditCardId) {
        return balanceService.getBalances(accountId, creditCardId);
    }

    @GetMapping("/consolidated/{customerId}")
    public Mono<ConsolidatedProductsResponse> getConsolidatedProducts(@PathVariable String customerId) {
        return balanceService.getConsolidatedProducts(customerId);
    }
    @GetMapping("/average-daily/{customerId}")
    public Mono<MonthlyAverageBalanceResponse> getMonthlyAverageBalance(@PathVariable String customerId) {
        return balanceService.getMonthlyAverageBalance(customerId);
    }

    @GetMapping("/commissions/report")
    public Mono<CommissionReportResponse> getCommissionReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String productType) {
        return balanceService.getCommissionReport(startDate, endDate, productType);
    }
}

package com.example.msbalanceinquiriservice.controller;


import com.example.msbalanceinquiriservice.dto.BalanceResponse;
import com.example.msbalanceinquiriservice.dto.ConsolidatedProductsResponse;
import com.example.msbalanceinquiriservice.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

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
}

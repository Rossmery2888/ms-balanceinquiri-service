package com.example.msbalanceinquiriservice.service.Impl;

import com.example.msbalanceinquiriservice.dto.*;

import com.example.msbalanceinquiriservice.service.BalanceService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {

    private final WebClient.Builder webClientBuilder;

    @Override
    public Mono<BalanceResponse> getBalances(String accountId, String creditCardId) {
        Mono<AccountBalance> accountBalanceMono = webClientBuilder.build()
                .get()
                .uri("http://localhost:8081/api/accounts/{accountId}/balance", accountId)
                .retrieve()
                .bodyToMono(AccountBalance.class);

        Mono<CreditCardBalance> creditCardBalanceMono = webClientBuilder.build()
                .get()
                .uri("http://localhost:8082/api/credit-cards/{creditCardId}/balance", creditCardId)
                .retrieve()
                .bodyToMono(CreditCardBalance.class);

        return Mono.zip(accountBalanceMono, creditCardBalanceMono)
                .map(tuple -> new BalanceResponse(tuple.getT1(), tuple.getT2()));
    }

    @Override
    public Mono<ConsolidatedProductsResponse> getConsolidatedProducts(String customerId) {
        Mono<List<Account>> accountsMono = webClientBuilder.build()
                .get()
                .uri("http://localhost:8081/api/accounts/customer/{customerId}", customerId)
                .retrieve()
                .bodyToFlux(Account.class)
                .collectList();

        Mono<List<CreditCard>> creditCardsMono = webClientBuilder.build()
                .get()
                .uri("http://localhost:8082/api/credit-cards/customer/{customerId}", customerId)
                .retrieve()
                .bodyToFlux(CreditCard.class)
                .collectList();

        return Mono.zip(accountsMono, creditCardsMono)
                .map(tuple -> new ConsolidatedProductsResponse(customerId, tuple.getT1(), tuple.getT2()));
    }
}
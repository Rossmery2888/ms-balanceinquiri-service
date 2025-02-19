package com.example.msbalanceinquiriservice.service.Impl;


import com.example.msbalanceinquiriservice.dto.*;
import com.example.msbalanceinquiriservice.model.Commission;
import com.example.msbalanceinquiriservice.model.DailyBalance;
import com.example.msbalanceinquiriservice.repository.CommissionRepository;
import com.example.msbalanceinquiriservice.repository.DailyBalanceRepository;
import com.example.msbalanceinquiriservice.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {

    private final WebClient.Builder webClientBuilder;
    private final DailyBalanceRepository dailyBalanceRepository;
    private final CommissionRepository commissionRepository;

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

    @Override
    public Mono<MonthlyAverageBalanceResponse> getMonthlyAverageBalance(String customerId) {
        // Obtener el mes actual
        YearMonth currentYearMonth = YearMonth.now();
        LocalDate startDate = currentYearMonth.atDay(1);
        LocalDate endDate = currentYearMonth.atEndOfMonth();

        // Obtener todos los registros de saldo diario para el cliente en el mes actual
        return dailyBalanceRepository.findByCustomerIdAndDateBetween(customerId, startDate, endDate)
                .collectList()
                .flatMap(dailyBalances -> {
                    if (dailyBalances.isEmpty()) {
                        return Mono.just(new MonthlyAverageBalanceResponse(
                                customerId,
                                currentYearMonth.getMonth().toString(),
                                currentYearMonth.getYear(),
                                new ArrayList<>()
                        ));
                    }

                    // Agrupar por productId y calcular el saldo promedio
                    Map<String, List<DailyBalance>> balancesByProduct = dailyBalances.stream()
                            .collect(Collectors.groupingBy(DailyBalance::getProductId));

                    List<ProductAverageBalance> productsAverage = balancesByProduct.entrySet().stream()
                            .map(entry -> {
                                String productId = entry.getKey();
                                List<DailyBalance> productBalances = entry.getValue();
                                String productType = productBalances.get(0).getProductType();

                                // Calcular promedio
                                BigDecimal totalBalance = productBalances.stream()
                                        .map(balance -> BigDecimal.valueOf(balance.getBalance()))
                                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                                BigDecimal average = totalBalance.divide(
                                        BigDecimal.valueOf(productBalances.size()),
                                        2,
                                        RoundingMode.HALF_UP
                                );

                                return new ProductAverageBalance(productId, productType, average);
                            })
                            .collect(Collectors.toList());

                    return Mono.just(new MonthlyAverageBalanceResponse(
                            customerId,
                            currentYearMonth.getMonth().toString(),
                            currentYearMonth.getYear(),
                            productsAverage
                    ));
                });
    }

    @Override
    public Mono<CommissionReportResponse> getCommissionReport(LocalDate startDate, LocalDate endDate, String productType) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        Flux<Commission> commissionsFlux;

        // Filtrar por tipo de producto si se especifica
        if (productType != null && !productType.isEmpty()) {
            commissionsFlux = commissionRepository.findByProductTypeAndDateBetween(
                    productType, startDateTime, endDateTime);
        } else {
            commissionsFlux = commissionRepository.findByDateBetween(startDateTime, endDateTime);
        }

        return commissionsFlux.collectList()
                .map(commissions -> {
                    if (commissions.isEmpty()) {
                        return new CommissionReportResponse(
                                startDate,
                                endDate,
                                new ArrayList<>(),
                                BigDecimal.ZERO
                        );
                    }

                    // Agrupar comisiones por producto
                    Map<String, List<Commission>> commissionsByProduct = commissions.stream()
                            .collect(Collectors.groupingBy(Commission::getProductId));

                    // Crear resumen por producto
                    List<ProductCommissionSummary> productSummaries = commissionsByProduct.entrySet().stream()
                            .map(entry -> {
                                String productId = entry.getKey();
                                List<Commission> productCommissions = entry.getValue();
                                String prodType = productCommissions.get(0).getProductType();

                                // Calcular total de comisiones para este producto
                                BigDecimal totalAmount = productCommissions.stream()
                                        .map(Commission::getAmount)
                                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                                // Convertir Commission a CommissionDTO
                                List<CommissionDTO> commissionDTOs = productCommissions.stream()
                                        .map(commission -> new CommissionDTO(
                                                commission.getId(),
                                                commission.getProductId(),
                                                commission.getProductType(),
                                                commission.getCustomerId(),
                                                commission.getAmount(),
                                                commission.getDescription(),
                                                commission.getDate()
                                        ))
                                        .collect(Collectors.toList());

                                return new ProductCommissionSummary(productId, prodType, totalAmount, commissionDTOs);
                            })
                            .collect(Collectors.toList());

                    // Calcular total general de comisiones
                    BigDecimal totalCommissions = productSummaries.stream()
                            .map(ProductCommissionSummary::getTotalAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    return new CommissionReportResponse(startDate, endDate, productSummaries, totalCommissions);
                });
    }
}
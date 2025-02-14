package com.example.msbalanceinquiriservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsolidatedProductsResponse {
    private String customerId;
    private List<Account> accounts;
    private List<CreditCard> creditCards;
}
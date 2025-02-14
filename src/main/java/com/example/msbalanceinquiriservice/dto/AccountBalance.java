package com.example.msbalanceinquiriservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountBalance {
    private String accountId;
    private BigDecimal balance;
}
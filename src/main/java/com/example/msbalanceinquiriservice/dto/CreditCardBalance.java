package com.example.msbalanceinquiriservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardBalance {
    private String creditCardId;
    private BigDecimal balance;
}

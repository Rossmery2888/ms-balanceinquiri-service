package com.example.msbalanceinquiriservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyAverageBalanceDTO {
    private String customerId;
    private String productId;
    private String productType;
    private BigDecimal averageBalance;
    private LocalDate date;
}

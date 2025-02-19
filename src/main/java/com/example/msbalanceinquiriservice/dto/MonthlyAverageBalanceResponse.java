package com.example.msbalanceinquiriservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyAverageBalanceResponse {
    private String customerId;
    private String month;
    private int year;
    private List<ProductAverageBalance> products;
}

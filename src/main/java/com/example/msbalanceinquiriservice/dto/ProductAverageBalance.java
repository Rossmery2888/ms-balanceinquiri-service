package com.example.msbalanceinquiriservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductAverageBalance {
    private String productId;
    private String productType;
    private BigDecimal averageBalance;
}

package com.example.msbalanceinquiriservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCommissionSummary {
    private String productId;
    private String productType;
    private BigDecimal totalAmount;
    private List<CommissionDTO> details;
}

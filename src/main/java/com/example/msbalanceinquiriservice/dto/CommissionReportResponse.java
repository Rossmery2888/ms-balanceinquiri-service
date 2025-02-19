package com.example.msbalanceinquiriservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommissionReportResponse {
    private LocalDate startDate;
    private LocalDate endDate;
    private List<ProductCommissionSummary> commissionsByProduct;
    private BigDecimal totalCommissions;
}
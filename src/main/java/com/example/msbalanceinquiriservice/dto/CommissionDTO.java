package com.example.msbalanceinquiriservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommissionDTO {
    private String id;
    private String productId;
    private String productType;
    private String customerId;
    private BigDecimal amount;
    private String description;
    private LocalDateTime date;
}

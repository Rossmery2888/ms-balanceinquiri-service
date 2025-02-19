package com.example.msbalanceinquiriservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Document(collection = "commissions")
public class Commission {
    @Id
    private String id;
    private String customerId;
    private String productId;
    private String productType;
    private BigDecimal amount;
    private String description;
    private LocalDateTime date;
}

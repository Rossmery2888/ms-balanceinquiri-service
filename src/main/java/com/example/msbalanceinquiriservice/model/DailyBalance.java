package com.example.msbalanceinquiriservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "daily_balances")
public class DailyBalance {
    @Id
    private String id;
    private String customerId;
    private String productId;
    private String productType;
    private Double balance;
    private LocalDate date;
}
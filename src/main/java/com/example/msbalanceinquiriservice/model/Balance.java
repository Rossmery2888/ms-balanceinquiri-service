package com.example.msbalanceinquiriservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "balances")
public class Balance {
    @Id
    private String id;
    private String clientId;
    private String productId;
    private String productType; // SAVINGS_ACCOUNT, CHECKING_ACCOUNT, FIXED_TERM, CREDIT_CARD
    private Double availableBalance;
    private Double currentBalance;
}
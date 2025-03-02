package com.microfinance.loan_services.models;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Loan {
    @Id
    private Long id;
    // Other fields and getters/setters
}

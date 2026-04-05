package com.finance.finance_backend.dto.request;

import com.finance.finance_backend.enums.TransactionType;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private Double amount;

    @NotNull(message = "Transaction type is required")
    private TransactionType type;

    @NotBlank(message = "Category is required")
    private String category;

    private String note;

    @NotNull(message = "Date is required")
    private LocalDate date;
}

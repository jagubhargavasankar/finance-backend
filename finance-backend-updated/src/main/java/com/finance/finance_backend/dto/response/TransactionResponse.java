package com.finance.finance_backend.dto.response;

import com.finance.finance_backend.enums.TransactionType;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponse {
    private Long id;
    private Double amount;
    private TransactionType type;
    private String category;
    private String note;
    private LocalDate date;
}

package com.finance.finance_backend.dto.response;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponse {
    private Double totalIncome;
    private Double totalExpense;
    private Double netBalance;
    private List<TransactionResponse> recentTransactions;
}

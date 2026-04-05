package com.finance.finance_backend;

import com.finance.finance_backend.entity.Transaction;
import com.finance.finance_backend.enums.TransactionType;
import com.finance.finance_backend.service.DashboardService;
import com.finance.finance_backend.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FinanceBackendApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testTransactionTypeEnum() {
        assertEquals("INCOME", TransactionType.INCOME.name());
        assertEquals("EXPENSE", TransactionType.EXPENSE.name());
    }

    @Test
    void testTransactionBuilderFields() {
        Transaction t = Transaction.builder()
                .amount(500.0)
                .type(TransactionType.INCOME)
                .category("Salary")
                .note("Monthly salary")
                .build();

        assertNotNull(t);
        assertEquals(500.0, t.getAmount());
        assertEquals(TransactionType.INCOME, t.getType());
        assertEquals("Salary", t.getCategory());
    }
}

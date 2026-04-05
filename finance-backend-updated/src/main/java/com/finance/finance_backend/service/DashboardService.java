package com.finance.finance_backend.service;

import com.finance.finance_backend.dto.response.DashboardResponse;
import com.finance.finance_backend.dto.response.TransactionResponse;
import com.finance.finance_backend.entity.Transaction;
import com.finance.finance_backend.entity.User;
import com.finance.finance_backend.enums.TransactionType;
import com.finance.finance_backend.exception.ResourceNotFoundException;
import com.finance.finance_backend.repository.TransactionRepository;
import com.finance.finance_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionService transactionService;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Authenticated user not found"));
    }

    public DashboardResponse getSummary() {
        User user = getCurrentUser();
        List<Transaction> list = transactionRepository.findByUserId(user.getId());

        double income  = 0;
        double expense = 0;
        for (Transaction t : list) {
            if (t.getType() == TransactionType.INCOME) income  += t.getAmount();
            else                                        expense += t.getAmount();
        }

        List<TransactionResponse> recent = list.stream()
                .sorted((a, b) -> b.getDate().compareTo(a.getDate()))
                .limit(5)
                .map(transactionService::mapToResponse)
                .toList();

        return DashboardResponse.builder()
                .totalIncome(income)
                .totalExpense(expense)
                .netBalance(income - expense)
                .recentTransactions(recent)
                .build();
    }

    public Map<String, Double> getCategoryWiseData() {
        User user = getCurrentUser();
        List<Transaction> list = transactionRepository.findByUserId(user.getId());
        Map<String, Double> map = new LinkedHashMap<>();
        for (Transaction t : list) {
            map.merge(t.getCategory(), t.getAmount(), Double::sum);
        }
        return map;
    }

    public Map<String, Double> getTrends() {
        User user = getCurrentUser();
        List<Transaction> list = transactionRepository.findByUserId(user.getId());

        return list.stream()
                .collect(Collectors.groupingBy(
                        t -> t.getDate().getYear() + "-"
                                + String.format("%02d", t.getDate().getMonthValue()),
                        TreeMap::new,
                        Collectors.summingDouble(Transaction::getAmount)
                ));
    }
}

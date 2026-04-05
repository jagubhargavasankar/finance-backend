package com.finance.finance_backend.service;

import com.finance.finance_backend.dto.request.TransactionRequest;
import com.finance.finance_backend.dto.response.TransactionResponse;
import com.finance.finance_backend.entity.Transaction;
import com.finance.finance_backend.entity.User;
import com.finance.finance_backend.exception.ResourceNotFoundException;
import com.finance.finance_backend.repository.TransactionRepository;
import com.finance.finance_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Authenticated user not found"));
    }

    public TransactionResponse createTransaction(TransactionRequest request) {
        User user = getCurrentUser();
        Transaction transaction = Transaction.builder()
                .amount(request.getAmount())
                .type(request.getType())
                .category(request.getCategory())
                .note(request.getNote())
                .date(request.getDate())
                .user(user)
                .build();
        return mapToResponse(transactionRepository.save(transaction));
    }

    public Page<TransactionResponse> getAllTransactions(
            String type, String category,
            String startDate, String endDate,
            int page, int size) {

        User user = getCurrentUser();
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());

        LocalDate start = startDate != null ? LocalDate.parse(startDate) : null;
        LocalDate end   = endDate   != null ? LocalDate.parse(endDate)   : null;

        List<Transaction> all = transactionRepository.findByUserId(user.getId());

        List<TransactionResponse> filtered = all.stream()
                .filter(t -> type == null || t.getType().name().equalsIgnoreCase(type))
                .filter(t -> category == null || t.getCategory().equalsIgnoreCase(category))
                .filter(t -> start == null || !t.getDate().isBefore(start))
                .filter(t -> end   == null || !t.getDate().isAfter(end))
                .sorted((a, b) -> b.getDate().compareTo(a.getDate()))
                .map(this::mapToResponse)
                .toList();

        int from = (int) pageable.getOffset();
        int to   = Math.min(from + pageable.getPageSize(), filtered.size());

        List<TransactionResponse> pageContent = from >= filtered.size()
                ? List.of()
                : filtered.subList(from, to);

        return new PageImpl<>(pageContent, pageable, filtered.size());
    }

    public TransactionResponse updateTransaction(Long id, TransactionRequest request) {
        User user = getCurrentUser();
        Transaction transaction = transactionRepository
                .findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + id));
        transaction.setAmount(request.getAmount());
        transaction.setType(request.getType());
        transaction.setCategory(request.getCategory());
        transaction.setNote(request.getNote());
        transaction.setDate(request.getDate());
        return mapToResponse(transactionRepository.save(transaction));
    }

    public void deleteTransaction(Long id) {
        User user = getCurrentUser();
        Transaction transaction = transactionRepository
                .findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + id));
        transactionRepository.delete(transaction);
    }

    public TransactionResponse mapToResponse(Transaction t) {
        return TransactionResponse.builder()
                .id(t.getId())
                .amount(t.getAmount())
                .type(t.getType())
                .category(t.getCategory())
                .note(t.getNote())
                .date(t.getDate())
                .build();
    }
}

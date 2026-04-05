package com.finance.finance_backend.repository;

import com.finance.finance_backend.entity.Transaction;
import com.finance.finance_backend.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUserId(Long userId);

    Optional<Transaction> findByIdAndUserId(Long id, Long userId);

    List<Transaction> findByUserIdAndType(Long userId, TransactionType type);

    List<Transaction> findByUserIdAndCategory(Long userId, String category);

    List<Transaction> findByUserIdAndDateBetween(Long userId, LocalDate start, LocalDate end);

    List<Transaction> findByUserIdAndTypeAndCategory(Long userId, TransactionType type, String category);
}
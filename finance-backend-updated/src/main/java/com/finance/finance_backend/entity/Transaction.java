package com.finance.finance_backend.entity;
import com.finance.finance_backend.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
@Entity
@Data
@Table(name = "transactions")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Double amount;
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    @Column(nullable = false)
    private String category;
    private String note;
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
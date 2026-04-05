package com.finance.finance_backend.entity;
import com.finance.finance_backend.enums.RoleName;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    private RoleName role;
    private Boolean active = true;

}
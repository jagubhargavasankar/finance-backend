package com.finance.finance_backend.dto.request;
import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    private String email;
    private String password;
}
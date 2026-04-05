package com.finance.finance_backend.dto.response;
import lombok.*;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {

    private LocalDateTime timestamp;
    private String message;
    private int status;
}
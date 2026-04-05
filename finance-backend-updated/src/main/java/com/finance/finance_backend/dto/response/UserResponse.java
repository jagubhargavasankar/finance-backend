package com.finance.finance_backend.dto.response;

import com.finance.finance_backend.enums.RoleName;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private RoleName role;
    private Boolean active;
}

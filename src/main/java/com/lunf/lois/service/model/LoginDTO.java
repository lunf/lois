package com.lunf.lois.service.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDTO {
    private Long id;
    private String username;
    private String passwordHash;
    private LoginType loginType;
    private String token;
}

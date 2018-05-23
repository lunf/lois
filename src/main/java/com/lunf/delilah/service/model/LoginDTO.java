package com.lunf.delilah.service.model;

import lombok.Data;

@Data
public class LoginDTO {
    private Long id;
    private String username;
    private String passwordHash;
    private LoginType loginType;
    private String token;
}

package com.lunf.lois.service.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String token;
}

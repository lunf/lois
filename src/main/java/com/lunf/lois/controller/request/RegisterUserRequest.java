package com.lunf.lois.controller.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterUserRequest implements Serializable {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
}

package com.lunf.lois.data.primary.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class LfLogin implements Serializable {
    private Long id;
    private String username;
    private String passwordHash;
    private LfUser user;
    private int loginType;
    private String token;
}

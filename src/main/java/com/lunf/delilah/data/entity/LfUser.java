package com.lunf.delilah.data.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class LfUser implements Serializable {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
}

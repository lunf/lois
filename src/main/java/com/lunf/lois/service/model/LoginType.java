package com.lunf.lois.service.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum LoginType {

    STANDARD(1), API_CALL(2);

    private final int value;

    public static LoginType parseValue(int value) {
        for(LoginType type : LoginType.values()) {
            if (type.value == value) {
                return type;
            }
        }

        // Default return STANDARD
        return STANDARD;
    }

}

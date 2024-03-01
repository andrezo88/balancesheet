package com.andre.balancesheet.util.constant;

import lombok.Getter;

@Getter
public enum StringsConstants {
    AUTHORIZATION("Authorization"),
    BEARER("Bearer "),
    USER_ID("userId");

    private final String description;
    StringsConstants(String description) {
        this.description = description;
    }

}

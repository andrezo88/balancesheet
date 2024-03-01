package com.andre.balancesheet.util.constant;

import lombok.Getter;

@Getter
public enum IntConstants {

    DIFFERENCE_DATES(1),
    NO_DIFFERENCE_DATE(0);


    private final int value;

    IntConstants(int value) {
        this.value = value;
    }
}

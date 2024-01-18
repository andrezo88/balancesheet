package com.andre.balancesheet.fixtures;

import com.andre.balancesheet.model.User;

public class UserFixture {

    public static final User userDefault = User.builder()
            .id("1234")
            .firstname("Usuario")
            .lastname("Teste")
            .email("email@email.com")
            .password("senha")
            .build();
}

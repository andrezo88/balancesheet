package com.andre.balancesheet.utils.mappers;

import com.andre.balancesheet.dto.UserResponseDto;
import com.andre.balancesheet.fixtures.UserFixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserMapperTest {

    @Test
    void shouldReturnUserResponseDto() {

        var user = UserFixture.userDefaultEntityAdminRole;

        UserResponseDto response = UserFixture.INSTANCE_MAPPER.convertUserToUserResponse(user);

        assertEquals(user.getFirstname(), response.firstname());
        assertEquals(user.getLastname(), response.lastname());
        assertEquals(user.getEmail(), response.email());
        assertEquals(user.getRole(), response.role());
    }

    @Test
    void shouldReturnNullWhenBalanceModelIsNull() {

        UserResponseDto response = UserFixture.INSTANCE_MAPPER.convertUserToUserResponse(null);

        assertNull(response);
    }
}

package com.andre.balancesheet.service;

import com.andre.balancesheet.config.auth.JwtService;
import com.andre.balancesheet.dto.RegisterRequest;
import com.andre.balancesheet.exceptions.service.BadRequestException;
import com.andre.balancesheet.exceptions.service.IdNotFoundException;
import com.andre.balancesheet.fixtures.UserFixture;
import com.andre.balancesheet.model.User;
import com.andre.balancesheet.repository.UserRepository;
import com.andre.balancesheet.util.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Spy
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationService service;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AdminService adminService;

    @Test
    void shouldUpdateUserRole() {
        RegisterRequest dto = UserFixture.userDefaultDtoUserAdmin;
        User entity = UserFixture.userDefaultEntityUserRole;
        User entityAdminRole = UserFixture.userDefaultEntityAdminRole;
        var userResponseDto = userMapper.convertUserToUserResponse(entityAdminRole);

        when(userRepository.findById("1")).thenReturn(Optional.of(entity));
        when(userRepository.save(entity)).thenReturn(entityAdminRole);
        when(userMapper.convertUserToUserResponse(entityAdminRole)).thenReturn(userResponseDto);

        var result = adminService.updateUserRole("1", dto);

        assertThat(result).isEqualTo(userResponseDto);
    }

    @Test
    void shouldThrowExceptionWhenIdNotExists() {
        String userId = "1";

        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> adminService.updateUserRole(userId, any()))
                .isInstanceOf(IdNotFoundException.class)
                .hasMessageContaining("Id %s not found: ", userId);
        verify(userRepository).findById(userId);
    }

    @Test
    void shouldThrowBadRequestWhenUserIsAdmin() {
        var userId = "1";
        var userEntity = UserFixture.userDefaultEntityAdminRole;

        when(userRepository.findById(anyString())).thenReturn(Optional.of(userEntity));

        assertThatThrownBy(() -> adminService.updateUserRole(userId, any()))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("User %s is Admin", userEntity.getEmail());
        verify(userRepository).findById(userId);
    }
}

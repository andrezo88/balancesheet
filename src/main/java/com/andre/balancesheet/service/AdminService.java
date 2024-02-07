package com.andre.balancesheet.service;

import com.andre.balancesheet.config.auth.JwtService;
import com.andre.balancesheet.dto.RegisterRequest;
import com.andre.balancesheet.dto.UserResponseDto;
import com.andre.balancesheet.exceptions.service.BadRequestException;
import com.andre.balancesheet.exceptions.service.IdNotFoundException;
import com.andre.balancesheet.model.Role;
import com.andre.balancesheet.repository.UserRepository;
import com.andre.balancesheet.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository repository;
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final UserMapper mapper;


    public UserResponseDto updateUserRole(String userId, RegisterRequest dto){
        var user = repository.findById(userId)
                .orElseThrow(() -> new IdNotFoundException(
                        String.format("Id %s not found: ", userId))
                );
        if(user.getRole().equals(Role.ADMIN)) {
            throw new BadRequestException(String.format("User %s is Admin", user.getEmail()));
        }
        user.setRole(dto.getRole());
        repository.save(user);
        return mapper.convertUserToUserResponse(user);
    }
}
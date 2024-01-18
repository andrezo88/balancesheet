package com.andre.balancesheet.service;

import com.andre.balancesheet.config.auth.JwtService;
import com.andre.balancesheet.dto.AuthenticationRequest;
import com.andre.balancesheet.dto.AuthenticationResponse;
import com.andre.balancesheet.dto.RegisterRequest;
import com.andre.balancesheet.exceptions.service.BadRequestException;
import com.andre.balancesheet.exceptions.service.IdNotFoundException;
import com.andre.balancesheet.model.Role;
import com.andre.balancesheet.model.User;
import com.andre.balancesheet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {

        isEmailRegistered(request.getEmail());
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IdNotFoundException(
                        String.format("Email %s not found: ",request.getEmail()))
                );
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public User getUser() {
        var user = SecurityContextHolder.getContext().getAuthentication();
        var userEntity = userRepository.findByEmail(user.getName());
        return userEntity.orElse(null);
    }

    public void isEmailRegistered(String email) {
        var user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            throw new BadRequestException(String.format("Email %s is already registered", email));
        }
    }
}

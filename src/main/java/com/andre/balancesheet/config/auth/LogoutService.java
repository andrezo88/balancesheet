package com.andre.balancesheet.config.auth;

import com.andre.balancesheet.model.Token;
import com.andre.balancesheet.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authorization = "Authorization";
        final String bearer = "Bearer ";
        final String authHeader = request.getHeader(authorization);
        final String jwt;
        final int removeBearerWordLength = 7;
        if (authHeader == null || !authHeader.startsWith(bearer)) {
            return;
        }
        jwt = authHeader.substring(removeBearerWordLength);
        var storedToken = tokenRepository.findByToken(jwt).orElse(null);
        updateTokenToExpireAndRevoked(storedToken);
    }

    private void updateTokenToExpireAndRevoked(Token storedToken) {
        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
        }
    }
}

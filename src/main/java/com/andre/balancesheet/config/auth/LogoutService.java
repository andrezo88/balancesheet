package com.andre.balancesheet.config.auth;

import com.andre.balancesheet.model.Token;
import com.andre.balancesheet.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import static com.andre.balancesheet.util.constant.StringsConstants.AUTHORIZATION;
import static com.andre.balancesheet.util.constant.StringsConstants.BEARER;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader(AUTHORIZATION.getDescription());
        if (authHeader == null || !authHeader.startsWith(BEARER.getDescription())) {
            return;
        }
        var storedToken = getStoredToken(authHeader);
        updateTokenToExpireAndRevoked(storedToken);
    }

    private Token getStoredToken(String authHeader) {
        final String jwt = authHeader.substring(BEARER.getDescription().length());
        return tokenRepository.findByToken(jwt).orElse(null);
    }

    private void updateTokenToExpireAndRevoked(Token storedToken) {
        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
        }
    }
}

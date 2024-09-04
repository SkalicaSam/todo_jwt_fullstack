package com.example.springJwt.config;

import com.example.springJwt.model.Token;
import com.example.springJwt.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLogoutHandler implements LogoutHandler {

    private final TokenRepository tokenRepository;

    public CustomLogoutHandler(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }


    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {


        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        String token = authHeader.substring(7);
//        get stored token from db
        Token storedToken = tokenRepository.findByToken(token).orElse(null);  // orElser(null) =meaning=   if token is not found, return null
//        invailidat the token i.e.make logout true
        if (token != null){
            storedToken.setLoggedOut(true);
            tokenRepository.save(storedToken);
        }
//        save the token

    }
}

package com.example.springJwt.controller;

import com.example.springJwt.model.AuthenticationResponse;
import com.example.springJwt.model.User;
import com.example.springJwt.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody User request
        ) {
        return ResponseEntity.ok(authService.register(request));
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody User request
    ){
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Odstranit "Bearer " z tokenu
            System.out.println("Received token for logout: " + token);
            // Zde můžete provést operace, například zneplatnění tokenu
            // authService.invalidateToken(token);

            // Vymažeme kontext autentizace
            SecurityContextHolder.clearContext();
        }

        // Vrátíme odpověď 200 OK
        return ResponseEntity.ok("Logout successful");
    }
}

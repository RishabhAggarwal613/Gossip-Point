package com.gossip_point.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.gossip_point.app.config.TokenProvider;
import com.gossip_point.app.entity.User;
import com.gossip_point.app.repository.UserRepository;
import com.gossip_point.app.request.LoginRequest;
import com.gossip_point.app.response.AuthResponse;
import com.gossip_point.app.service.CustomUserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final CustomUserService customUserService;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider,
                          AuthenticationManager authenticationManager, CustomUserService customUserService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.customUserService = customUserService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> registerUser(@Valid @RequestBody User user) {
        try {
            if (user.getEmail() == null || userRepository.findByEmail(user.getEmail()) != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                     .body(new AuthResponse("Email is already in use or invalid", false));
            }

            // Save new user
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);

            // Generate JWT Token directly
            String jwt = tokenProvider.generateTokenFromEmail(user.getEmail());
            return ResponseEntity.ok(new AuthResponse(jwt, true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new AuthResponse("Error processing request", false));
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginRequest req) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
            );

            String jwt = tokenProvider.generateTokenFromEmail(req.getEmail());
            return ResponseEntity.ok(new AuthResponse(jwt, true));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body(new AuthResponse("Invalid email or password", false));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new AuthResponse("Authentication failed", false));
        }
    }
}

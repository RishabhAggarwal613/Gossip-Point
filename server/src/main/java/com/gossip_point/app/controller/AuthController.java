package com.gossip_point.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"}, 
             allowedHeaders = "*", 
             methods = {RequestMethod.GET, RequestMethod.POST})
public class AuthController {
  @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private TokenProvider tokenProvider;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserService customUserService;

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
        // Validate input
        if (user.getEmail() == null || user.getPassword() == null || user.getFull_name() == null) {
            return ResponseEntity.badRequest()
                .body(new AuthResponse("All fields are required", false, false));
        }

        // Check if user exists
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            return ResponseEntity.badRequest()
                .body(new AuthResponse("Email is already registered", false, false));
        }

        // Create new user
        User newUser = new User();
        newUser.setEmail(user.getEmail().toLowerCase());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setFull_name(user.getFull_name());
        
        User savedUser = userRepository.save(newUser);

        // Generate JWT token
        String token = tokenProvider.generateToken(savedUser.getEmail());
        
        return ResponseEntity.ok(new AuthResponse(token, true, true));
        
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new AuthResponse(e.getMessage(), false, false));
    }
}
        @PostMapping("/signin")
    public ResponseEntity<AuthResponse> loginUser(@Valid @RequestBody LoginRequest req) {
        try {
            // Validate input
            if (req.getEmail() == null || req.getPassword() == null) {
                return ResponseEntity.badRequest()
                    .body(new AuthResponse("Email and password are required", false, false));
            }

            // Authenticate user - Fixed static call
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
            );

            // Generate token - Fixed casing
            String token = tokenProvider.generateToken(req.getEmail());
            return ResponseEntity.ok(new AuthResponse(token, true, false));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new AuthResponse("Invalid email or password", false, false));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new AuthResponse("Authentication failed: " + e.getMessage(), false, false));
        }
    }
}

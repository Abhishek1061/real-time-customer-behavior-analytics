package com.realtimeEcho.UserService.service;

import com.realtimeEcho.UserService.dto.AuthResponse;
import com.realtimeEcho.UserService.dto.LoginRequest;
import com.realtimeEcho.UserService.dto.RegisterRequest;
import com.realtimeEcho.UserService.dto.UserResponse;
import com.realtimeEcho.UserService.exception.BadRequestException;
import com.realtimeEcho.UserService.model.User;
import com.realtimeEcho.UserService.repository.UserRepository;
import com.realtimeEcho.UserService.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");
        User saved = userRepository.save(user);

        return new UserResponse(saved.getId(), saved.getUsername(), saved.getEmail(), saved.getRole());
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        String token = jwtUtil.generateToken(request.getUsername());
        return new AuthResponse(token);
    }
}

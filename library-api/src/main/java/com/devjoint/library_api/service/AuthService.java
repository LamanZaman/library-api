package com.devjoint.library_api.service;

import com.devjoint.library_api.dto.AuthResponse;
import com.devjoint.library_api.dto.LoginRequest;
import com.devjoint.library_api.dto.RegisterRequest;
import com.devjoint.library_api.entity.Role;
import com.devjoint.library_api.entity.User;
import com.devjoint.library_api.repository.UserRepository;
import com.devjoint.library_api.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already taken");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        User saved = userRepository.save(user);

        String token = jwtUtil.generateToken(saved.getUsername(), saved.getRole().name());
        return new AuthResponse(token, saved.getUsername(), saved.getRole().name());
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());
        return new AuthResponse(token, user.getUsername(), user.getRole().name());
    }
}
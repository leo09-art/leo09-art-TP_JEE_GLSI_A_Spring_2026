package com.example.bankega.controller;

import com.example.bankega.component.JwtUtil;
import com.example.bankega.dto.LoginRequest;
import com.example.bankega.dto.RefreshRequest;
import com.example.bankega.entity.User;
import com.example.bankega.exception.ResourceNotFoundException;
import com.example.bankega.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("")
public class Login {
    private  final JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Login(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/api/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody LoginRequest loginRequest){
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé")));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.get().getPassword())){
            throw  new RuntimeException("Invalids credentials");
        }

        String accessToken = jwtUtil.genretedAccessToken(user.get().getUsername());
        String refreshToken = jwtUtil.genretedRefreshToken(user.get().getUsername());
        return ResponseEntity.ok(Map.of("accessToken", accessToken, "refreshToken", refreshToken));
    }

    @PostMapping("api/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshRequest refreshRequest){

        try{
            String username = jwtUtil.extractUsername(refreshRequest.getRefreshToken());
            String newAccessToken = jwtUtil.genretedAccessToken(username);
            return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
        } catch (ExpiredJwtException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Refresh token expired");
        }
    }
}

package com.lit.games_storage.controllers;

import com.lit.games_storage.dtos.TokenResponseDTO;
import com.lit.games_storage.dtos.LoginRequestDTO;
import com.lit.games_storage.dtos.RegisterRequestDTO;
import com.lit.games_storage.models.UserModel;
import com.lit.games_storage.security.auth.token.JwtUtil;
import com.lit.games_storage.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDTO loginRequestDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getUsername(),
                        loginRequestDTO.getPassword()
                )
        );

        // Instanciando token
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok(new TokenResponseDTO(jwtUtil.generateAccessToken(loginRequestDTO.getUsername())));
    }

    @PostMapping("/register")
    public ResponseEntity<UserModel> registerUser(@RequestBody @Valid RegisterRequestDTO requestDTO) {
        return ResponseEntity.ok(userService.registerUser(requestDTO));
    }

}

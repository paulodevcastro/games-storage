package com.lit.games_storage.controllers;

import com.lit.games_storage.dtos.AuthResponseDTO;
import com.lit.games_storage.dtos.LoginRequestDTO;
import com.lit.games_storage.dtos.RegisterRequestDTO;
import com.lit.games_storage.models.UserModel;
import com.lit.games_storage.repositories.UserRepository;
import com.lit.games_storage.security.JwtUtil;
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
    private final UserRepository userRepository;
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

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateAccessToken(loginRequestDTO.getUsername());
        return ResponseEntity.ok(new AuthResponseDTO(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<UserModel> registerUser(@RequestBody @Valid RegisterRequestDTO requestDTO) {
        return ResponseEntity.ok(userService.registerUser(requestDTO));
    }

}

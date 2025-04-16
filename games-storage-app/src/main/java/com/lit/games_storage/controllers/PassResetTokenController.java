package com.lit.games_storage.controllers;

import com.lit.games_storage.payload.PassResetRequest;
import com.lit.games_storage.services.PassResetTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class PassResetTokenController {

    private final PassResetTokenService tokenService;

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> request){
        String email = request.get("email");
        tokenService.sendResetLink(email);
        return ResponseEntity.ok("Link de refinição de senha enviado para " + email);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody PassResetRequest request){
        tokenService.resetPassword(request.getToken(), request.getNewPassword());
        return ResponseEntity.ok("Senha redefinida com sucesso");
    }

}

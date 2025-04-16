package com.lit.games_storage.services;

import com.lit.games_storage.models.PassResetTokenModel;
import com.lit.games_storage.models.UserModel;
import com.lit.games_storage.repositories.PassResetTokenRepository;
import com.lit.games_storage.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PassResetTokenService {

    private final UserRepository userRepository;
    private final PassResetTokenRepository tokenRepositoy;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;

    public void sendResetLink(String email) {
        UserModel user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));

        String token = UUID.randomUUID().toString();
        PassResetTokenModel resetToken = new PassResetTokenModel();
        resetToken.setToken(token);
        resetToken.setAppUser(user);
        resetToken.setExpiration(LocalDateTime.now().plusHours(2));

        tokenRepositoy.save(resetToken);

        String resetLink = "http://localhost:3000/reset-password?token=" + token;

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Redefinição de Senha");
        mailMessage.setText("Clique aqui para redefinir a sua senha: " + resetLink);

        mailSender.send(mailMessage);
    }

    public void resetPassword(String token, String newPassword){
        PassResetTokenModel resetToken = tokenRepositoy.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token inválido"));

        if (resetToken.getExpiration().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("Token expirado");
        }

        UserModel user = resetToken.getAppUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // Invalida o token
        tokenRepositoy.delete(resetToken);
    }

}

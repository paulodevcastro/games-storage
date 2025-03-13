package com.lit.games_storage.services;

import com.lit.games_storage.dtos.LoginRequestDTO;
import com.lit.games_storage.dtos.RegisterRequestDTO;
import com.lit.games_storage.models.UserModel;
import com.lit.games_storage.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserModel registerUser(RegisterRequestDTO register){
        UserModel newUser = new UserModel();
        newUser.setFirstName(register.getFirstName());
        newUser.setLastName(register.getLastName());
        newUser.setEmail(register.getEmail());
        newUser.setUsername(register.getUsername());
        newUser.setPassword(passwordEncoder.encode(register.getPassword()));
        return userRepository.save(newUser);
    }

    public Optional<LoginRequestDTO> getUserById(Long id){
        return userRepository.findById(id)
                .map(userModel -> new LoginRequestDTO());
    }
}

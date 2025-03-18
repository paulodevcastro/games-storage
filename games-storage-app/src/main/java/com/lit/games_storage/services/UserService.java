package com.lit.games_storage.services;

import com.lit.games_storage.dtos.RegisterRequestDTO;
import com.lit.games_storage.dtos.UserDTO;
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

    public Optional<UserDTO> getUserById(Long id){
        return userRepository.findById(id)
                .map(user -> {
                    UserDTO dto = new UserDTO();
                    dto.setId(user.getId());
                    dto.setFirstName(user.getFirstName());
                    dto.setLastName(user.getLastName());
                    dto.setEmail(user.getEmail());
                    dto.setUsername(user.getUsername());
                    return dto;
                });
    }
}

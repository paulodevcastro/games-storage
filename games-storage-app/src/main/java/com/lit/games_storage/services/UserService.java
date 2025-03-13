package com.lit.games_storage.services;

import com.lit.games_storage.models.UserModel;
import com.lit.games_storage.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("usuário não encontrado: " + username));
    }

    public UserModel saveUser(UserModel userModel) {
        userModel.setPassword(new BCryptPasswordEncoder().encode(userModel.getPassword()));
        return userRepository.save(userModel);
    }

}

package com.lit.games_storage.controllers;

import com.lit.games_storage.dtos.UserDTO;
import com.lit.games_storage.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable(value = "id") Long id) {
        Optional<UserDTO> userDTOOptional = userService.getUserById(id);
        return userDTOOptional.<ResponseEntity<Object>>map(
                userDTO -> ResponseEntity.status(HttpStatus.OK).body(userDTO)).orElseGet(
                        () -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Esse usuário não existe!"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") Long id, @RequestBody @Valid UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

}

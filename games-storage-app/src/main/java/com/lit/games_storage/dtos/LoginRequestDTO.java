package com.lit.games_storage.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequestDTO {

    @NotBlank(message = "O nome de usuário é obrigatório")
    private String username;

    @NotBlank(message = "A senha é obrigatória")
    private String password;
}

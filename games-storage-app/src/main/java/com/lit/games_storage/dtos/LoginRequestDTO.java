package com.lit.games_storage.dtos;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequestDTO {

    private String username;
    private String password;

}

package com.lit.games_storage.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class PassResetTokenModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private LocalDateTime expiration;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel appUser;

}

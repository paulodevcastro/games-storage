package com.lit.games_storage.repositories;

import com.lit.games_storage.models.PassResetTokenModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PassResetTokenRepository extends JpaRepository<PassResetTokenModel, Long> {
    Optional<PassResetTokenModel> findByToken(String token);
}

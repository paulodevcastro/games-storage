package com.lit.games_storage.payload;

import lombok.Data;

@Data
public class PassResetRequest {
    private String token;
    private String newPassword;
}

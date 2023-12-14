package com.spring.authentication.services;

import com.spring.authentication.models.RefreshToken;

public interface RefreshTokenService {
    
    RefreshToken createRefreshToken(String username);
    RefreshToken verifyRefreshToken(String refreshToken);
}

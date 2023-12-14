package com.spring.authentication.serviceImpl;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.authentication.models.RefreshToken;
import com.spring.authentication.models.User;
import com.spring.authentication.repository.RefreshTokenRepo;
import com.spring.authentication.repository.UserRepo;
import com.spring.authentication.services.RefreshTokenService;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService{

    //private long refreshTokenValidity = 5*60*60*1000;
    private long refreshTokenValidity = 60*1000;

    @Autowired
    private RefreshTokenRepo refreshTokenRepo;
    @Autowired
    private UserRepo userRepo;

    @Override
    public RefreshToken createRefreshToken(String username) {

        User user = userRepo.findByUsername(username);
        RefreshToken refreshToken = user.getRefreshToken();

        if(refreshToken==null){
            refreshToken = RefreshToken.builder()
                    .refreshToken(UUID.randomUUID().toString())
                    .expiry(Instant.now().plusMillis(refreshTokenValidity))
                    .user(userRepo.findByUsername(username))
                    .build();
        }
        else{
            refreshToken.setExpiry(Instant.now().plusMillis(refreshTokenValidity));
        }
        user.setRefreshToken(refreshToken);

        refreshTokenRepo.save(refreshToken);
        return refreshToken;
    }

    @Override
    public RefreshToken verifyRefreshToken(String refreshToken) {
        RefreshToken reToken = refreshTokenRepo.findByRefreshToken(refreshToken).orElseThrow(()-> new RuntimeException("Refresh token does not exists!!"));
        if(reToken.getExpiry().compareTo(Instant.now())<0){
            refreshTokenRepo.delete(reToken);
            throw new RuntimeException("Refresh token invalid");
        }
        return reToken;
    }
    
}

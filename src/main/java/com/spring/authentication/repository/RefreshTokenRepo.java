package com.spring.authentication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.authentication.models.RefreshToken;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken,String>{
    Optional<RefreshToken> findByRefreshToken(String token);
}

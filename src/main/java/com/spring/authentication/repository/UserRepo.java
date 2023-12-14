package com.spring.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.authentication.models.User;

public interface UserRepo extends JpaRepository<User,Integer>{
    public User findByUsername(String username);
}
package com.spring.authentication.services;

import java.util.List;

import com.spring.authentication.dto.UserDto;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto getUserById(Integer id);
    UserDto registerUser(UserDto userDto);
}

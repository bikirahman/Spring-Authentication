package com.spring.authentication.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.authentication.dto.UserDto;
import com.spring.authentication.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    private ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> userDto = this.userService.getAllUsers();
		return new ResponseEntity<>(userDto,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private ResponseEntity<UserDto> getUserById(@PathVariable("id") Integer id){
        UserDto userDto = this.userService.getUserById(id);
		return new ResponseEntity<UserDto>(userDto,HttpStatus.OK);
    }
}

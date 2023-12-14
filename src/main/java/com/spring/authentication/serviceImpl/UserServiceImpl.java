package com.spring.authentication.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.authentication.dto.UserDto;
import com.spring.authentication.exception.UserNotFoundException;
import com.spring.authentication.models.User;
import com.spring.authentication.repository.UserRepo;
import com.spring.authentication.services.UserService;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = this.userRepo.findAll();
        List<UserDto> userDtos = users.stream().map((user)-> this.mapper.map(user, UserDto.class)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public UserDto getUserById(Integer id) {
        User user = this.userRepo.findById(id).orElseThrow(()->new UserNotFoundException("User not found : "+id));
        return this.mapper.map(user, UserDto.class);
    }

    @Override
	public UserDto registerUser(UserDto userDto) {
		User user = this.mapper.map(userDto, User.class);
		user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRole("ROLE_ADMIN");
		User resUser = this.userRepo.save(user);
		return this.mapper.map(resUser, UserDto.class);
	}
    
}

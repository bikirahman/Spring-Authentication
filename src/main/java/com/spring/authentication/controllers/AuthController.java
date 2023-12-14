package com.spring.authentication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.authentication.config.JwtTokenHelper;
import com.spring.authentication.config.MyUserDetailsService;
import com.spring.authentication.config.RefreshTokenRequest;
import com.spring.authentication.dto.UserDto;
import com.spring.authentication.models.RefreshToken;
import com.spring.authentication.models.User;
import com.spring.authentication.payload.JwtAuthRequest;
import com.spring.authentication.payload.JwtAuthResponse;
import com.spring.authentication.services.RefreshTokenService;
import com.spring.authentication.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
	private JwtTokenHelper jwtHelper;

	@Autowired
	private MyUserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@Autowired
	private RefreshTokenService refreshTokenService;

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest jwtAuthRequest) throws Exception {
		this.authenticate(jwtAuthRequest.getUsername(), jwtAuthRequest.getPassword());
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtAuthRequest.getUsername());
		String generateToken = this.jwtHelper.generateToken(userDetails);
		RefreshToken refreshToken = this.refreshTokenService.createRefreshToken(userDetails.getUsername());
		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(generateToken);
		response.setRefreshToken(refreshToken.getRefreshToken());
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
	}

	private void authenticate(String username, String password) throws Exception {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				username, password);
		try {
			this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);

		} catch (BadCredentialsException e) {
			System.out.println("Invalid user details!");
			throw new Exception("Invalid user details!");
		}

	}

	@PostMapping("/register")
	public ResponseEntity<UserDto> register(@RequestBody UserDto userDto) {
		UserDto registerUser = this.userService.registerUser(userDto);
		return new ResponseEntity<UserDto>(registerUser, HttpStatus.CREATED);
	}

	@PostMapping("/refresh-token")
	public JwtAuthResponse refreshToken(@RequestBody RefreshTokenRequest request){
		RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(request.getRefreshToken());

		User user = refreshToken.getUser();
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(user.getUsername());

		String token = this.jwtHelper.generateToken(userDetails);

		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		response.setRefreshToken(refreshToken.getRefreshToken());
		return response;
	}
}

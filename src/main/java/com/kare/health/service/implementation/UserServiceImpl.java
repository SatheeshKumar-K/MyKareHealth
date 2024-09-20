package com.kare.health.service.implementation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kare.health.dto.LoginDto;
import com.kare.health.dto.LoginResponseDTO;
import com.kare.health.dto.UserReqDto;
import com.kare.health.model.User;
import com.kare.health.repository.UserRepository;
import com.kare.health.security.TokenProvider;
import com.kare.health.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenProvider tokenProvider;

	@Override
	public ResponseEntity<?> save(UserReqDto userReqDto) throws Exception {

		Map<String, Object> response = new HashMap<>();
		HttpStatus httpStatus;

		Optional<User> userExist = userRepository.findByEmail(userReqDto.getEmail());
		if (userExist.isPresent()) {
			response.put("message", "Email Already Exist: " + userReqDto.getEmail());
			httpStatus = HttpStatus.CONFLICT;
			return new ResponseEntity(response, httpStatus);
		}

		try {

			User user = new User();
			user.setUserName(userReqDto.getUserName());
			user.setGender(userReqDto.getGender());
			user.setEmail(userReqDto.getEmail());
			user.setPassword(passwordEncoder.encode(userReqDto.getPassword()));
			user.setRole("User");
			user.setStatus(1);

			User userResponse = userRepository.save(user);

			response.put("content", userResponse);
			response.put("message", "Registration successful! Welcome to " + userResponse.getUserName());
			httpStatus = HttpStatus.CREATED;

		} catch (Exception e) {
			e.printStackTrace();
			response.put("message", "Exception occurred while running register API");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity(response, httpStatus);

	}

	public ResponseEntity<?> createAuthenticationToken(LoginDto loginDto) throws Exception {

		Map<String, Object> response = new HashMap<>();
		HttpStatus httpStatus;
		String token = null;
		LoginResponseDTO loginResponse = new LoginResponseDTO();

		Authentication authentication = authenticate(loginDto.getEmail(), loginDto.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		User userModel = userRepository.findByUserName(loginDto.getEmail());

		// generate claims for jwt token
		Map<String, Object> claims = new HashMap<>();
		claims.put("acl_role_id", userModel.getRole());
		claims.put("acl_user_id", userModel.getUserId());
		claims.put("name", userModel.getUserName());
		claims.put("portal", userModel.getRole());
		claims.put("user_type", "Admin");

		token = tokenProvider.generateToken(authentication, claims);

		// response from database
		loginResponse.setUserId(userModel.getUserId());
		loginResponse.setUserName(userModel.getUserName());
		loginResponse.setGender(userModel.getGender());
		loginResponse.setEmail(userModel.getEmail());
		loginResponse.setRole(userModel.getRole());
		loginResponse.setStatus(userModel.getRole());
		loginResponse.setToken(token);

		response.put("content", loginResponse);
		response.put("message", "Login Success");
		httpStatus = HttpStatus.OK;

		return new ResponseEntity(response, httpStatus);

	}

	private Authentication authenticate(String username, String password) throws Exception {
		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(username, password));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			return authentication;

		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("Email or Password Invalid", e);
		}

	}

	@Override
	public ResponseEntity<?> findAll() throws Exception {
		Map<String, Object> response = new HashMap<>();
		HttpStatus httpStatus;

		try {
			response.put("content", userRepository.findAll());
			response.put("message", " Success");
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			response.put("message", "Exception occur while running getall API");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity(response, httpStatus);
	}

	@Override
	public ResponseEntity<?> delete(String email) throws Exception {
		Map<String, Object> response = new HashMap<>();
		HttpStatus httpStatus;

		Optional<User> userExist = userRepository.findByEmail(email);
		if (!userExist.isPresent()) {
			response.put("message", "Email is not Exist: " + email);
			httpStatus = HttpStatus.CONFLICT;
			return new ResponseEntity(response, httpStatus);
		}

		try {
			userRepository.deleteByEmail(email);
			response.put("message", "Successfully Deleted");
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			response.put("message", "Exception occur while running delete API");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity(response, httpStatus);
	}

}

package com.kare.health.service;

import org.springframework.http.ResponseEntity;

import com.kare.health.dto.LoginDto;
import com.kare.health.dto.UserReqDto;

public interface UserService {

	public ResponseEntity<?> save(UserReqDto userReqDto) throws Exception;

	public ResponseEntity<?> createAuthenticationToken(LoginDto loginDto) throws Exception;

	public ResponseEntity<?> findAll() throws Exception;
	
	public ResponseEntity<?> delete(String email) throws Exception;


}

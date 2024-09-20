package com.kare.health.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kare.health.dto.LoginDto;
import com.kare.health.dto.UserReqDto;
import com.kare.health.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	public UserService userService;

	@PostMapping(value = "/save")
	public ResponseEntity<?> save(@Valid @RequestBody UserReqDto userReqDto) throws Exception {
		return userService.save(userReqDto);
	}

	@PostMapping(value = "/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginDto authenticationRequest) throws Exception {
		return userService.createAuthenticationToken(authenticationRequest);
	}

	@GetMapping("/getAll")
	public ResponseEntity<?> getAll() throws Exception {
		return userService.findAll();
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> delete(@RequestParam String email) throws Exception {
		return userService.delete(email);
	}

}

package com.kare.health.dto;

import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
	
    @Email(message = "Invalid email format")
	private String email;
	private String password;
}
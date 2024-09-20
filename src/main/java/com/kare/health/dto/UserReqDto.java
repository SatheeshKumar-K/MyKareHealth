package com.kare.health.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReqDto {

	private String userName;

	private String gender;

	@Email(message = "Invalid email format")
	@NotBlank(message = "Email is mandatory")
	private String email;

	@NotBlank(message = "Email is mandatory")
	private String password;

}

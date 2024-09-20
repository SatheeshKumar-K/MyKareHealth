package com.kare.health.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {

	private Long userId;

	private String userName;

	private String email;

	private String gender;

	private String role;

	private String status;

	private String token;

}

package com.demo.calculator.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
	private String accessToken;
	private String tokenType = "Bearer";
	private String username;
	private String name;

	public JwtResponse(String token, String username, String name) {
		this.accessToken = token;
		this.username = username;
		this.name = name;
	}
}

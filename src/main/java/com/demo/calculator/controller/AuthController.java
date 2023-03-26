package com.demo.calculator.controller;

import com.demo.calculator.request.LoginFormRequest;
import com.demo.calculator.service.UserService;
import com.demo.calculator.exception.ValidationException;
import com.demo.calculator.response.JwtResponse;
import com.demo.calculator.response.UserDetailsImpl;
import com.demo.calculator.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder encoder;

	private final JwtUtil jwtUtil;

	private final UserService userService;

	public AuthController(AuthenticationManager authenticationManager, PasswordEncoder encoder,
						  JwtUtil jwtUtil, UserService userService) {
		this.authenticationManager = authenticationManager;
		this.encoder = encoder;
		this.jwtUtil = jwtUtil;
		this.userService = userService;
	}

	@PostMapping("/signin")
	@Operation(summary = "Authenticate and retrieve a JWT token")
	public ResponseEntity<JwtResponse> authenticate(@RequestBody @Valid LoginFormRequest login,
													BindingResult errors) {

		if (errors.hasErrors()) {
			throw new ValidationException(errors);
		}

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtUtil.generateJwtToken(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		return ResponseEntity
				.ok(new JwtResponse(token, userDetails.getUsername(), userDetails.getName()));
	}

}

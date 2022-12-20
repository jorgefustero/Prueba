package com.example.drago.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.drago.model.dto.LoginRequest;
import com.example.drago.services.TokenService;

@RestController
@RequestMapping("/api")
public class AuthController {
	
	private final TokenService tokenService;
	private final AuthenticationManager authenticationManager;
	
	public AuthController(TokenService tokenService, AuthenticationManager authenticationManager) {
		this.tokenService = tokenService;
		this.authenticationManager = authenticationManager;
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest userLogin) {	
		try {
			Authentication authentication = authenticationManager.authenticate(
							new UsernamePasswordAuthenticationToken(userLogin.user(), userLogin.password()));
			return ResponseEntity.ok().body(tokenService.generateToken(authentication)); 
		} catch (Exception e) {
		    return ResponseEntity
		    		.status(HttpStatus.INTERNAL_SERVER_ERROR)
		    		.body(e.getMessage());
		}
	}

}
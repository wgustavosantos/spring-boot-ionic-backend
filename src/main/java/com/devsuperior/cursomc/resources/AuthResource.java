package com.devsuperior.cursomc.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.cursomc.domain.dtos.EmailDTO;
import com.devsuperior.cursomc.security.JWTUtil;
import com.devsuperior.cursomc.security.UserSS;
import com.devsuperior.cursomc.services.AuthService;
import com.devsuperior.cursomc.services.UserService;

/*Necessário passar o token na requição do endpoint */
@RestController
@RequestMapping(value = "/auth")
public class AuthResource {
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private AuthService authService;
	
	@PostMapping(value = "/refresh_token")
	public ResponseEntity<Void> refreshToken(HttpServletResponse response){
		
		UserSS user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " +  token);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping(value = "/forgot")
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDto){
		
		authService.sendNewPassword(objDto.getEmail());
		
		return ResponseEntity.noContent().build();
	}

}

package com.huytmb.mail.receiver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.huytmb.mail.receiver.model.JwtRequest;
import com.huytmb.mail.receiver.model.JwtResponse;
import com.huytmb.mail.receiver.service.JwtService;

@RestController
@CrossOrigin
public class JwtController {
	
	@Autowired
	private JwtService jwts;
	
	@PostMapping({"/auth"})
	public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception{
		return jwts.createJwtToken(jwtRequest);
	}

}

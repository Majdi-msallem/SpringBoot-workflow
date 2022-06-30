package com.huytmb.mail.receiver.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.huytmb.mail.receiver.model.User;
import com.huytmb.mail.receiver.service.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService us;

	 //lancer la methode depuis le demarrage de lapplication
	/*@PostConstruct
	public void initRolesAndUSers(){
		us.initRolesAndUSer();
	}*/
	@PostMapping({"/register"})
	public User register(@RequestBody User user){
		return us.register(user);
	}
	
	@GetMapping({"/admin"})
	@PreAuthorize("hasRole('admin')")
	public String forAdmin (){
		return "this URL is only accessible to admin";
	}
	
	@GetMapping({"/user"})
	//@PreAuthorize("hasRole('User')")
	public String foruser (){
		return "this URL is only accessible to user";
	}
	
	
	
}

package com.huytmb.mail.receiver.controller;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.huytmb.mail.receiver.model.User;
import com.huytmb.mail.receiver.repository.UserRepositroy;
import com.huytmb.mail.receiver.service.UserService;
import com.huytmb.mail.receiver.util.JwtUtil;

@RestController
public class UserController {
	@Autowired
	private UserService us;
	@Autowired
	private JwtUtil ju;
	

	 //lancer la methode depuis le demarrage de lapplication
	/*@PostConstruct
	public void initRolesAndUSers(){
		us.initRolesAndUSer();
	}*/
	@PostMapping({"/register"})
	public User register(@RequestBody User user){
		return us.register(user);
	}
	
		@GetMapping("/getuser")
	   @ResponseBody
	    public User getuserFromRequest(HttpServletRequest request) {
	     
	    return ju.getuserFromRequest(request);
	    }
		
		@GetMapping("/getuserById/{id}")
		   @ResponseBody
		    public Optional<User> getuser (@PathVariable int id) {
				return us.getUserById(id);
		    }
		@GetMapping("/getalluser")
		   @ResponseBody
		    public List<User> getAllUser() {
		     return (List<User>) us.getAllUsers();
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

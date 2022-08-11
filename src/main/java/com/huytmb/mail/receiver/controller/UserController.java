package com.huytmb.mail.receiver.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.huytmb.mail.receiver.model.User;
import com.huytmb.mail.receiver.service.UserService;
import com.huytmb.mail.receiver.util.JwtUtil;
import com.huytmb.mail.receiver.util.Utility;

@RestController 
public class UserController {
	@Autowired
	private UserService us;
	@Autowired
	private JwtUtil ju;
	@Autowired
	private Utility ut;
	

	 //lancer la methode depuis le demarrage de lapplication
/*	@PostConstruct
	public void initRolesAndUSers(){
		us.initRolesAndUSer();
	}*/
	@DeleteMapping("/deleteUser/{id}")
	@ResponseBody
	public void removeuser(@PathVariable("id") int id) {
	us.deleteUser(id);
	}
	@PostMapping({"/register"})
	public User register(@RequestBody User user,HttpServletRequest request) throws AddressException, MessagingException, IOException{
		 us.register(user);
		String siteUrl=ut.getSiteUrl(request);
		 us.sendVerificationEmail(user, siteUrl);
		 return user;
	}
	
	@GetMapping("/verify")
	public String VerifyAccount(@RequestParam("code")String code){
		boolean verified = us.Verify(code);
		
				String pageTitle = verified ? "verification succeeded" : "verification failed";
			return "verifier";
	}
	
	
		@GetMapping("/getuser")
	   @ResponseBody
	    public User getuserFromRequest(HttpServletRequest request) {
	     
	    return ju.getuserFromRequest(request);
	    }
		
		@GetMapping("/getuserName")
		   @ResponseBody
		    public String getuserName(HttpServletRequest request,String name) {
		     
		    return name= ju.getuserFromRequest(request).getUserName();
		    }
		
		
		@GetMapping("/getuserById/{id}")
		   @ResponseBody
		    public Optional<User> getuser (@PathVariable int id) {
				return us.getUserById(id);
		    }
		@GetMapping("/getalluser")
		   @ResponseBody
		    public Page<User> getAllUser(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,@RequestParam(required = false)String recherche) {
		  PageRequest pr=PageRequest.of(page, size);
		  return us.getAllUsers(pr,recherche);
		    }
		@GetMapping("/getalltechuser")
		   @ResponseBody
		    public List<User> getAllUserbyroleName() {
					
		     return (List<User>) us.getAllUsersByroleName();
		    }
		
		@PutMapping("/updateuser")
		@ResponseBody
		public ResponseEntity<?> updateuser (@RequestBody User u) {
			return us.update(u);
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

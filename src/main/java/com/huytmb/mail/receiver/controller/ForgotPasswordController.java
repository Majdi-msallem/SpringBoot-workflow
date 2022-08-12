package com.huytmb.mail.receiver.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import com.huytmb.mail.receiver.service.UserEmailNotFoundException;
import com.huytmb.mail.receiver.service.UserService;

import net.bytebuddy.utility.RandomString;

@Controller
public class ForgotPasswordController {
	
		@Autowired
		private UserService us;

	
	
	@PostMapping ("/forgotpassword")
	public String processForgetPassword(HttpServletRequest request ,Model model){
		String email = request.getParameter("email");
		String token = RandomString.make(45);
		try{
			us.updateResetPassword(token, email);
			//generate resetpassword lik
			//send email 
		}catch (UserEmailNotFoundException ex){
			//e.printStackTrace();
			model.addAttribute("error", ex.getMessage());
		}
		
		//System.out.println("Email:::"+email);
		//System.out.println("tok:::"+token);
		return "forgot_password";
	}

}

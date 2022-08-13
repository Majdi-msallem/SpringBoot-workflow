package com.huytmb.mail.receiver.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.huytmb.mail.receiver.model.User;
import com.huytmb.mail.receiver.service.UserEmailNotFoundException;
import com.huytmb.mail.receiver.service.UserService;
import com.huytmb.mail.receiver.util.Utility;

import net.bytebuddy.utility.RandomString;

@RestController
public class ForgotPasswordController {
	
		@Autowired
		private UserService us;
		@Autowired
		private Utility u;

	
	
	@PostMapping ("/forgotpassword")
	public String processForgetPassword(HttpServletRequest request,Model model){
		String email =request.getParameter("email");
		String token = RandomString.make(45);
		try{
			us.updateResetPassword(token, email);
			String resetPasswordLink = u.getSiteUrl(request)+"/resetpassword?token="+token;
			//System.out.println("liiink"+resetPasswordLink);
			us.sendResetPasswordnEmail(email, resetPasswordLink);
			model.addAttribute("message","We have sent a reset password Link to your email ");
			
		}catch (UserEmailNotFoundException ex){
			model.addAttribute("error", ex.getMessage());
		} catch (UnsupportedEncodingException | MessagingException e ) {
			model.addAttribute("error", "Error while sending reset password email");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println("Email:::"+email);
		//System.out.println("tok:::"+token);
		return "forgot password ";
	}
		
	
	@GetMapping("/resetPassword")
		public String ResetPassword(@Param(value = "token") String token , Model model){
			User user = us.get(token);
			if (user == null){
				model.addAttribute("title", "Reset your  Password");
				model.addAttribute("message", "Invalid Token for Reset Password");
				return "message";
			}
			model.addAttribute("token",token);
			return "resetpassword formulaire condition";
		}
	
	
		@PostMapping("/reset_password")
		public String ProcessResetPassword(HttpServletRequest request,Model model ){
			String token = request.getParameter("token");
			String Password = request.getParameter("Password");
			
			//passer le token on front
			User user = us.get(token);
			
			if (user == null){
				model.addAttribute("title", "Reset your  Password");
				model.addAttribute("message", "Invalid Token for Reset Password");
				
			}else{
				us.updatePassword(user, Password);
				model.addAttribute("message", "you have successfully changed your paswsord.");
			}
            	
				return "message";
	
		}
		
		
}

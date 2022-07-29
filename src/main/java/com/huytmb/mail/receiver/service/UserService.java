package com.huytmb.mail.receiver.service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.huytmb.mail.receiver.model.User;
import com.huytmb.mail.receiver.model.Role;

import com.huytmb.mail.receiver.repository.RoleRepository;
import com.huytmb.mail.receiver.repository.UserRepositroy;

import net.bytebuddy.utility.RandomString;

@Service
public class UserService {
	@Autowired
	private UserRepositroy ur;
	
	@Autowired
	private RoleRepository rr;
	
	@Autowired
	private PasswordEncoder pe;
	
  public User register(User  user) {
	  List<Integer> idrole= new ArrayList<Integer>(); 
	  for (Role role : user.getRole()) {
		idrole.add(role.getIdRole());
	}
	 // Role role =  rr.findByRoleName("rh");
	  List<Role> role =(List<Role>) rr.findAllById(idrole);
	  Set<Role> roles = new HashSet<>(role);
	  user.setRole(roles);
	  user.setEnabled(false);
	 String randomCode= RandomString.make(64);
	 user.setVerificationcode(randomCode);
	 user.setPassword(getEncodedPassword(user.getPassword()));
	  return ur.save(user);
  }

  public void sendVerificationEmail(User user,String siteUrl)throws AddressException, MessagingException, IOException{
	  Properties props = new Properties();
	   props.put("mail.smtp.auth", "true");
	   props.put("mail.smtp.starttls.enable", "true");
	   props.put("mail.smtp.host", "smtp.gmail.com");
	   props.put("mail.smtp.port", "587");
	   
	   Session session = Session.getInstance(props, new javax.mail.Authenticator() {
	      protected PasswordAuthentication getPasswordAuthentication() {
	         return new PasswordAuthentication("testrh022@gmail.com", "ztygkzhlotfhfhae");
	      }
	   });
	   
	   String mailContent = "<p>Dear: "+user.getUserName()+",</p> <br>";
	   mailContent +=" Please click the Link below to verify  to your registration </p> ";
	    String verifyUrl = siteUrl +"/verify?code=" +user.getVerificationcode();
	   mailContent +="<h3><a href=\""+verifyUrl+"\"> Verify</a> </h3>";
	   mailContent +="<p> Thank you<br> TrituxGroup </p>";
	   Message msg = new MimeMessage(session);
	   msg.setFrom(new InternetAddress("testrh022@gmail.com", false));

	   msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("majdi.msallem@esprit.tn"));
	   msg.setSubject("Please verify your registration");
	   msg.setContent(mailContent,"text/html");
			   

	  /* MimeBodyPart messageBodyPart = new MimeBodyPart();
	   messageBodyPart.setContent("Tutorials point email", "text/html");

	   Multipart multipart = new MimeMultipart();
	   multipart.addBodyPart(messageBodyPart);
	   MimeBodyPart attachPart = new MimeBodyPart();

	   attachPart.attachFile("/var/tmp/image19.png");
	   multipart.addBodyPart(attachPart);
	   msg.setContent(multipart);*/
	   Transport.send(msg);	
}

public Page<User> getAllUsers(PageRequest pr,String recherche){
			if (recherche.equals(""))	
	  return (Page<User>) ur.findAll(pr);
			 List<User> users= ur.findAll().stream()
				      .filter(user -> user.getUserName().contains(recherche) || user.getEmail().contains(recherche))
				      .collect(Collectors.toList());
			 int start = (int) pr.getOffset();
			 int end = (int) ((start + pr.getPageSize()) > users.size() ? users.size()
					   : (start + pr.getPageSize()));
			 Page<User> alluserpage = new PageImpl<>(users.subList(start, end),pr,users.size());
			 return alluserpage;
  }
  public Optional<User>  getUserById(int id){
	  return ur.findById(id);
  }
  
  public  List<User> getAllUsersByroleName(){
	  return ur.findAllUsersByroleName();
  }
  	public boolean Verify(String verificationcode){
  		User user= ur.FindByVerificationCode(verificationcode);
  		
  		if (user == null || user.isEnabled()){
  			return false;
  		}else{
  			ur.enable(user.getId());
  			
  			return true;
  		}
  	}
  	
  	public ResponseEntity<?>  update(User u) {
		User user=ur.findById(u.getId()).orElse(null);
		user.setUserFName(u.getUserFName());
		user.setUserLName(u.getUserLName());
		user.setUserName(u.getUserName());
		user.setEmail(u.getEmail());
		user.setPassword(getEncodedPassword(u.getPassword()));
		
		User U =ur.save(user);
		return ResponseEntity.ok(U) ;	
	}
  
  /* public void initRolesAndUSer(){
	  Role rhRole = new Role();
	  rhRole.setRoleName("rh");
	  rhRole.setRoleDescription("rh role");
	  rr.save(rhRole);
	  
	  
	  Role drhRole = new Role();
	  drhRole.setRoleName("d_rh");
	  drhRole.setRoleDescription("d_rh role");
	  rr.save(drhRole);
	  
	  Role techRole = new Role();
	  techRole.setRoleName("tech");
	  techRole.setRoleDescription(" tech role");
	  rr.save(techRole);

	  User adminUser = new User();
	  adminUser.setUserFName("fnadminu");
	  adminUser.setUserLName("lnadminu");
	  adminUser.setUserName("admiinname");
	  adminUser.setEmail("admin@gmail.com");
	  adminUser.setPassword(getEncodedPassword("admin123"));
	  Set<Role> adminRoles = new HashSet<>();
	  adminRoles.add(adminRole);
	  adminUser.setRole(adminRoles);
	  ur.save(adminUser);
	  
	  
	 User user = new User();
	  user.setUserFName("fnuser");
	  user.setUserLName("lnuser");
	  user.setUserName("useername");
	  user.setEmail("user@gmail.com");
	  user.setPassword(getEncodedPassword("user123"));
	  Set<Role> userRoles = new HashSet<>();
	  userRoles.add(userRole);
	  user.setRole(userRoles);
	  ur.save(user);
	
  }  */
  
  
  public String getEncodedPassword(String password){
	  return pe.encode(password);
  }




}

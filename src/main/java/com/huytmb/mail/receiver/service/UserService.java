package com.huytmb.mail.receiver.service;


import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.huytmb.mail.receiver.model.User;
import com.huytmb.mail.receiver.model.Role;

import com.huytmb.mail.receiver.repository.RoleRepository;
import com.huytmb.mail.receiver.repository.UserRepositroy;

@Service
public class UserService {
	@Autowired
	private UserRepositroy ur;
	
	@Autowired
	private RoleRepository rr;
	
	@Autowired
	private PasswordEncoder pe;
	
  public User register(User  user){
	  Role role =  rr.findByRoleName("User");
	  Set<Role> roles = new HashSet<>();
	  roles.add(role);
	  user.setRole(roles);
	 user.setPassword(getEncodedPassword(user.getPassword()));
	  return ur.save(user);
  }
  
  public void initRolesAndUSer(){
	  Role adminRole = new Role();
	  adminRole.setRoleName("admin");
	  adminRole.setRoleDescription("admin role");
	  rr.save(adminRole);
	  
	  
	  Role userRole = new Role();
	  userRole.setRoleName("User");
	  userRole.setRoleDescription("Default role for new user");
	  rr.save(userRole);
	  
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
	  
	  
	 /* User user = new User();
	  user.setUserFName("fnuser");
	  user.setUserLName("lnuser");
	  user.setUserName("useername");
	  user.setEmail("user@gmail.com");
	  user.setPassword(getEncodedPassword("user123"));
	  Set<Role> userRoles = new HashSet<>();
	  userRoles.add(userRole);
	  user.setRole(userRoles);
	  ur.save(user);*/
  }
  
  
  public String getEncodedPassword(String password){
	  return pe.encode(password);
  }
}
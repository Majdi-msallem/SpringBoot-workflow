package com.huytmb.mail.receiver.service;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
	  List<Integer> idrole= new ArrayList<Integer>();
	  Role role =  rr.findByRoleName("rh");
	  Set<Role> roles = new HashSet<>();
	  roles.add(role);
	  user.setRole(roles);
	 user.setPassword(getEncodedPassword(user.getPassword()));
	  return ur.save(user);
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

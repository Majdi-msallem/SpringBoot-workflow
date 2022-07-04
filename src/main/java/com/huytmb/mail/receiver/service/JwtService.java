package com.huytmb.mail.receiver.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.huytmb.mail.receiver.model.JwtRequest;
import com.huytmb.mail.receiver.model.JwtResponse;
import com.huytmb.mail.receiver.model.User;
import com.huytmb.mail.receiver.repository.UserRepositroy;
import com.huytmb.mail.receiver.util.JwtUtil;

@Service
public class JwtService implements UserDetailsService {

	@Autowired
	private UserRepositroy ur;
	
	@Autowired
	private JwtUtil jwtu;
	
	@Autowired
	private AuthenticationManager am;
	
	public JwtResponse createJwtToken(JwtRequest jwtRequest)throws Exception{
		String userName=jwtRequest.getUserName();
		String userPassword=jwtRequest.getUserpassword();
		authenticate(userName, userPassword);
		
	final	UserDetails userDetails = loadUserByUsername(userName);
		
		String newGeneratedToken=jwtu.generateToken(userDetails);
		User user = ur.findByUserName(userName);
		
		return new JwtResponse(user , newGeneratedToken);
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = ur.findByUserName(username);
		
		if(user!=null){
			return new org.springframework.security.core.userdetails.User(
					user.getUserName(),
					user.getPassword(), 
					getAuthorities(user));
		}else{
			throw new UsernameNotFoundException("user name is not valid");
		}
		
	}
	
	
	public User getFulluser (String username) throws UsernameNotFoundException{
		return ur.findByUserName(username);
	}
	
	private Set getAuthorities (User user){
		Set authorities = new HashSet();
		
		user.getRole().forEach(role ->{
			authorities.add(new SimpleGrantedAuthority("Role"+role.getRoleName()));
		});
		return authorities;
	}
	
	private void authenticate (String userName,String userPassword) throws Exception{
		try{
			am.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));

		}catch(DisabledException e){
			throw new Exception("User is disabled ");
		}catch(BadCredentialsException e ){
			throw new Exception("bad credentials from User");
		}
	}

}

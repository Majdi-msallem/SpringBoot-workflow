package com.huytmb.mail.receiver.config;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.huytmb.mail.receiver.service.JwtService;
import com.huytmb.mail.receiver.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

		@Autowired
		private JwtUtil jwtu;
		
		@Autowired
		private JwtService jwts;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
	final 	String header =request.getHeader("Authorization");
	
	String jwtToken =null;
	String userName = null;
	if (header != null && header.startsWith("Bearer ")){
		jwtToken = header.substring(7);
		
		try{
			userName = jwtu.getUserNameFromToken(jwtToken);
		}catch(IllegalArgumentException e){
			System.out.println("Unable to get JWT token");
		}catch(ExpiredJwtException e){
			System.out.println("jwt token is expired");
		}
	}else{
		System.out.println("Jwt token does not start with bearer ");
	}
	
	if(userName !=null && SecurityContextHolder.getContext().getAuthentication()==null){
		UserDetails userDetails =jwts.loadUserByUsername(userName);
			if(jwtu.validateToken(jwtToken, userDetails)){
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
				new UsernamePasswordAuthenticationToken(userDetails,
						null,
						userDetails.getAuthorities());
				
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
	}
	 filterChain.doFilter(request, response);
	}

}

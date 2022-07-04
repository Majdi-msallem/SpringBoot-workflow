package com.huytmb.mail.receiver.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.huytmb.mail.receiver.model.User;
import com.huytmb.mail.receiver.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	
	 @Autowired
	    private JwtService jwts;
	private static final String SECRET_KEY = "secretkey";
	private static final int TOKEN_VALIDITY = 3600*5;
	
	
	public String getUserNameFromToken(String token){
		 return getClaimFromToken(token, Claims::getSubject);
	}
	
		public User getuserFromRequest(HttpServletRequest request){
			String authorizationHeader = request.getHeader("Authorization");
			String token=null;
			String userName = null;
			if (authorizationHeader != null &&authorizationHeader.startsWith("Bearer")){
				token=authorizationHeader.substring(7);
				userName= getUserNameFromToken(token);
			}
			return token == null ? null : jwts.getFulluser(userName);
		}
	
	private <T> T getClaimFromToken(String token,Function<Claims, T> claimResolver){
		 final Claims claims =getAllClaimsFromToken(token);
		 return claimResolver.apply(claims);
	}
	
	private Claims getAllClaimsFromToken (String token){
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}
	
	public boolean validateToken (String token, UserDetails userDetails){
		String userName = getUserNameFromToken(token);
		return (userName.equals(userDetails.getUsername())&& !isTokenExpired(token));
	}
	
	private boolean isTokenExpired(String token){
	  final	Date expirationDate = getExpirationDateFromToken(token);
	 return expirationDate.before(new Date());
	}
	
	private Date getExpirationDateFromToken(String token){
		return getClaimFromToken(token,Claims::getExpiration);
	}
		public String generateToken(UserDetails userDetails){
			Map<String,Object> claims = new HashMap<>();
			return  Jwts.builder()
					.setClaims(claims)
					.setSubject(userDetails.getUsername())
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis()+ TOKEN_VALIDITY * 1000))
					.signWith(SignatureAlgorithm.HS512,SECRET_KEY)
					.compact();
		}
	
}

package com.huytmb.mail.receiver.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;


@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String userName;
	private String userFName;
	private String userLName;
	private String email;
	private String Password;
	private boolean enabled;
	private String verificationcode;
	private String resetPasswordToken;
	
	
	
	
	
	@ManyToMany(fetch =FetchType.EAGER, cascade=CascadeType.MERGE)
	private Set<Role> role;


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getUserFName() {
		return userFName;
	}


	public void setUserFName(String userFName) {
		this.userFName = userFName;
	}


	public String getUserLName() {
		return userLName;
	}


	public void setUserLName(String userLName) {
		this.userLName = userLName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return Password;
	}


	public void setPassword(String password) {
		Password = password;
	}


	public Set<Role> getRole() {
		return role;
	}


	public void setRole(Set<Role> role) {
		this.role = role;
	}


	public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	public String getVerificationcode() {
		return verificationcode;
	}


	public void setVerificationcode(String verificationcode) {
		this.verificationcode = verificationcode;
	}


	public String getResetPasswordToken() {
		return resetPasswordToken;
	}


	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}


	

	
	
	
	

}

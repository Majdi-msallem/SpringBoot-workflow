package com.huytmb.mail.receiver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huytmb.mail.receiver.model.Role;
import com.huytmb.mail.receiver.repository.RoleRepository;

@Service
public class RoleService {
	@Autowired
	private RoleRepository rp;
	
	public Role createNewRole(Role role){
		return rp.save(role)	;
		 }
	
	public List<Role> getAllRole(){
		return (List<Role>) rp.findAll();
	}

	

}

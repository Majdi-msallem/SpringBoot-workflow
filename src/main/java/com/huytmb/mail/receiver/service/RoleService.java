package com.huytmb.mail.receiver.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.huytmb.mail.receiver.model.Role;
import com.huytmb.mail.receiver.model.User;
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

	public void  deleteRole(int idRole){
		this.rp.deleteById(idRole);
	}

}

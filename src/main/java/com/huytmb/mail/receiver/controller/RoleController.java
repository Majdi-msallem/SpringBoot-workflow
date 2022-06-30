package com.huytmb.mail.receiver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.huytmb.mail.receiver.model.Role;
import com.huytmb.mail.receiver.service.RoleService;

@RestController
public class RoleController {
	
	@Autowired
	private RoleService rs;
	
	
	@PostMapping({"/createNewRole"})
	@ResponseBody
	public Role createNewRole(@RequestBody Role role){
	return  rs.createNewRole(role);	
	}

}

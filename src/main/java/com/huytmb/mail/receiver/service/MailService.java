package com.huytmb.mail.receiver.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huytmb.mail.receiver.model.User;
import com.huytmb.mail.receiver.model.mailModel;
import com.huytmb.mail.receiver.repository.MailRepo;
import com.huytmb.mail.receiver.repository.UserRepositroy;
import com.huytmb.mail.receiver.util.JwtUtil;

@Service
public class MailService {
	
	@Autowired
	private MailRepo mr;
	
	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private  ActivitiService acts;
	
	@Autowired
	private UserRepositroy ur;
	
	
	
	public List<mailModel> getAllMail(){
		return (List<mailModel>) mr.findAll();
	}
	
	public Optional<mailModel> getMailByID(int id){
		return mr.findById(id);
	}
	
	public Optional<mailModel> refuser_Condidature(HttpServletRequest request,int idMail,boolean fs){
		Optional<mailModel> mail=getMailByID(idMail);
		
		//User u =ActivitiService.
		
		return null;
	}
}

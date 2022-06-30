package com.huytmb.mail.receiver.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.huytmb.mail.receiver.model.mailModel;
import com.huytmb.mail.receiver.service.MailService;

@RestController
public class MailController {
	
	@Autowired
	private MailService ms;
	
	@GetMapping("/getAllMail")
	@ResponseBody
	public List<mailModel> getalldemande()
	{
	return ms.getAllMail() ;

	}
	
	@GetMapping("/getMailById/{id}")
	@ResponseBody
	public Optional<mailModel> GetMailById(@PathVariable int id){
		return ms.getMailByID(id);
	}
}

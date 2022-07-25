package com.huytmb.mail.receiver.controller;



import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.huytmb.mail.receiver.model.Etat;
import com.huytmb.mail.receiver.model.mailModel;
import com.huytmb.mail.receiver.service.ActivitiService;


@RestController
@RequestMapping("/process")
public class testController {
	@Autowired
	private  RuntimeService runtimeservice;
	
	@Autowired
	private ActivitiService acts;
	
	@Autowired
	private RepositoryService repositoryService;
	@PostMapping("/a")
	@ResponseBody
	public long ajouterper( )
	{ 
		//Deploye process
		repositoryService.createDeployment().addClasspathResource("processes/diagram.bpmn").deploy();
		//start process
	ProcessInstance instance = runtimeservice.startProcessInstanceByKey("myProcess");
	// Verify that we started a new process instance
	return repositoryService.createProcessDefinitionQuery().count();
          
	}
	//@Secured(value="rh")
	@RolesAllowed("rh")
	@GetMapping(value = "/start/{idMail}/{fs}/{note}/{etat}/{userName}")
	@ResponseBody
	public void startProcessInstance(@PathVariable int idMail,@PathVariable int fs, HttpServletRequest request,
			@PathVariable String note,@PathVariable Etat etat,@PathVariable String userName) {
		 acts.startProcess(idMail,fs,request,note,etat,userName);
	}
	
	@GetMapping("/getAllMailByRole")
	@ResponseBody
	public List<mailModel>  getAll_demande1Byrole (HttpServletRequest request)
	{
    return acts.getMailTraitement(request);
    
	}
	@GetMapping("/getAllMailByName")
	@ResponseBody 
	public List<mailModel>  getAll_demande1ByName(HttpServletRequest request)
	{
    return acts.getMailTrByName(request);
    
	}
	@GetMapping("/tr2/{idMail}/{note}/{etat}")
	@ResponseBody
	public mailModel trait2 (HttpServletRequest request,@PathVariable int idMail,@PathVariable String note,@PathVariable Etat etat)
	{
		
     return acts.Tr2mail(request, idMail, note, etat);
	} 
}

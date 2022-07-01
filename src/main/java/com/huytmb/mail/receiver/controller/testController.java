package com.huytmb.mail.receiver.controller;



import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.huytmb.mail.receiver.service.ActivitiService;


@RestController
@RequestMapping("/test")
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
	
	@PostMapping(value = "/process/{idMail}/{fs}/{roleName}/{cause}")
	@ResponseBody
	public void startProcessInstance(@PathVariable int idMail,@PathVariable int fs, @PathVariable String roleName,
			@PathVariable String cause) {
		 acts.startProcess(idMail,fs,roleName,cause);
	}
}

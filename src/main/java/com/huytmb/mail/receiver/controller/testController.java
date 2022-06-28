package com.huytmb.mail.receiver.controller;



import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/test")
public class testController {
	@Autowired
	private  RuntimeService runtimeservice;
	
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
}

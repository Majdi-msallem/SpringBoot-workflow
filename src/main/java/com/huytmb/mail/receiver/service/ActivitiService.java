package com.huytmb.mail.receiver.service;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huytmb.mail.receiver.model.Status;

@Service
public class ActivitiService {
	 @Autowired
	    private ManagementService managementService;


	    @Autowired
	    private RuntimeService runtimeService;
	    

	    @Autowired
	    private TaskService taskService;

	    @Autowired
	    private HistoryService historyService;

		@Autowired
		private RepositoryService repositoryService;
	    
	    
	    public void startProcess (int idMail, int fs,String roleName){
			   Map<String, Object> variables = new HashMap<String, Object>();
			   if(fs==1){
				   variables.put("fs",1);
				   variables.put("tech", roleName);
				   variables.put("idMail",idMail);

			   }else{
				   variables.put("fs",2);
				   variables.put("idMail",idMail);

			   }
				repositoryService.createDeployment().addClasspathResource("processes/diagram.bpmn").deploy();

			   ProcessInstance processInstance=runtimeService.startProcessInstanceByKey("myProcess",variables);
			   
			   System.out.println("Process started. Number of currently running"
				         + "process instances= "+ runtimeService.createProcessInstanceQuery().count());
	    }
}

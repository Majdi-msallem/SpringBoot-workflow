package com.huytmb.mail.receiver.service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huytmb.mail.receiver.model.Decision;
import com.huytmb.mail.receiver.model.Etat;
import com.huytmb.mail.receiver.model.Role;
import com.huytmb.mail.receiver.model.Status;
import com.huytmb.mail.receiver.model.Traitement;
import com.huytmb.mail.receiver.model.User;
import com.huytmb.mail.receiver.model.mailModel;
import com.huytmb.mail.receiver.repository.MailRepo;
import com.huytmb.mail.receiver.repository.TraitementRepo;
import com.huytmb.mail.receiver.util.JwtUtil;
import org.activiti.engine.task.Task;

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

	@Autowired
	private MailRepo mr;

	@Autowired
	private JwtUtil jwtu;
	
	@Autowired
	private TraitementRepo trr;

	
	
	//First step start procees ROLE RH accept/reject
	public void startProcess(int idMail, int fs,HttpServletRequest request,String note,Etat etat,String userName,String meet) {
		Map<String, Object> variables = new HashMap<String, Object>();
		User u = jwtu.getuserFromRequest(request);
			
		if (fs == 1) {
			variables.put("fs", 1);
			variables.put("tech",userName );
			variables.put("idMail", idMail);
			variables.put("meet", meet);
		

			mailModel mail = mr.findById(idMail).orElse(null);
			mail.setStatus(Status.encours);
			  
			Traitement tr1 = new Traitement();
			tr1.setGeneratedby(u.getUserName());
			tr1.setNote(note);
			tr1.setEtat(etat);
			trr.save(tr1);
			mail.setTr1(tr1); 
				mr.save(mail); 
		} else {
			variables.put("fs", 2);
			variables.put("idMail", idMail);
			variables.put(meet, "No meeting");
			mailModel mail = mr.findById(idMail).orElse(null);
			mail.setStatus(Status.traiter);
			Traitement tr1 = new Traitement();
			tr1.setGeneratedby(u.getUserName());
			tr1.setNote(note);
			tr1.setEtat(etat);
			trr.save(tr1);
			mail.setTr1(tr1);
				mr.save(mail);
		}
		repositoryService.createDeployment().addClasspathResource("processes/diagram.bpmn").deploy();

		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess", variables);

		System.out.println("Process started. Number of currently running" + "process instances= "
				+ runtimeService.createProcessInstanceQuery().count());
	}
	
	//get liste Tasks of mail By userNameConnected
	public List<Integer> getAllTaskByuserName(HttpServletRequest request) {
		User u = jwtu.getuserFromRequest(request);
		//String role = u.getRole().stream().findFirst().get().getRoleName();
		String name =u.getUserName();
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(name).list();
		List<Integer> idMail = new ArrayList<>();
		for (Task task : tasks) {
			System.out.println("Task available :" + task.getId());
			idMail.add((Integer) runtimeService.getVariables(task.getExecutionId()).get("idMail"));
			System.out.println(runtimeService.getVariables(task.getExecutionId()).get("idMail"));
		}
		System.out.println("mail id from the task" + idMail);
		return idMail;
	}
	
	public List<mailModel> getMailTrByName(HttpServletRequest request) {
		List<mailModel> mail = new ArrayList<>();
		List<Integer> idMail = getAllTaskByuserName(request);
		System.out.println(idMail);
		if (idMail.size() > 0) {
			for (Integer integer : idMail) {
				mail.add(mr.findById(integer).orElse(null));
			}
		}
		return mail;
	}
	
	 
	 
 
	//get liste Tasks of mail By RoleConnected 
	public List<Integer> getAllTaskByRole(HttpServletRequest request) {
		User u = jwtu.getuserFromRequest(request);
		String role = u.getRole().stream().findFirst().get().getRoleName();
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(role).list();
		List<Integer> idMail = new ArrayList<>();
		for (Task task : tasks) {
			System.out.println("Task available :" + task.getId());
			idMail.add((Integer) runtimeService.getVariables(task.getExecutionId()).get("idMail"));
			System.out.println(runtimeService.getVariables(task.getExecutionId()).get("idMail"));
		}
		System.out.println("mail id from the task" + idMail);
		return idMail;
	}
		
	public List<mailModel> getMailTraitement(HttpServletRequest request) {
		List<mailModel> mail = new ArrayList<>();
		List<Integer> idMail = getAllTaskByRole(request);
		System.out.println(idMail);
		if (idMail.size() > 0) {
			for (Integer integer : idMail) {
				mail.add(mr.findById(integer).orElse(null));
			}
		}
		return mail;
	}

	
//sending a task from tech to drh
	public User traitement2Mail(HttpServletRequest request, int idMail) {
		User u = jwtu.getuserFromRequest(request);
		String name=u.getUserName();
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(name).list();
		String taskid = null;
		Integer a = null;
		for (Task task : tasks) {
			a = (Integer) runtimeService.getVariables(task.getExecutionId()).get("idMail");
			System.out.println("aaaaaaaaaaaaaaaaaaaaaa" + a);
			if (a == idMail)
				System.out.println("task iddd traaaaitement 222ppppppp" + taskid);
			taskid = task.getId();
		}
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("d_rh", "d_rh");
		variables.put("idMail", idMail);
		taskService.complete(taskid,variables);
		return u;
	}

	public mailModel Tr2mail(HttpServletRequest request, int idMail, String note, Etat etat) {
		mailModel mail = mr.findById(idMail).orElse(null);
		Traitement tr2 = new Traitement();
		User u = traitement2Mail(request, idMail);
		tr2.setGeneratedby(u.getUserName());
		tr2.setNote(note);
		tr2.setEtat(etat);
		trr.save(tr2);
		mail.setTr2(tr2);
		mr.save(mail);
		return mail;
	}
	  
	//traitement final 
	public User traitementfinal(HttpServletRequest request, int idMail) {
		User u = jwtu.getuserFromRequest(request);
		String role = u.getRole().stream().findFirst().get().getRoleName();
		//String name =u.getUserName();
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(role).list();
		String taskid = null;
		Integer a = null;
		for (Task task : tasks) {
			a = (Integer) runtimeService.getVariables(task.getExecutionId()).get("idMail");
				if (a == idMail)
			taskid = task.getId();
		}
		Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("idMail", idMail);
		  taskService.complete(taskid,variables);
		return u;
	}
	public mailModel Trfinalmail(HttpServletRequest request, int idMail,int ls, String note, Etat etat) {
		mailModel mail = mr.findById(idMail).orElse(null);
		Map<String, Object> variables = new HashMap<String, Object>();
		User u = jwtu.getuserFromRequest(request);

		if (ls == 1) {
			variables.put("ls", 1);
			variables.put("idMail", idMail);
			mail.setStatus(Status.traiter);
			Traitement tr3 = new Traitement();
			tr3.setGeneratedby(u.getUserName());
			tr3.setNote(note);
			tr3.setEtat(etat);
			trr.save(tr3);
			mail.setTr3(tr3);
				mr.save(mail);
		}else{
			variables.put("ls", 2);
			variables.put("idMail", idMail);
			mail.setStatus(Status.traiter);
			Traitement tr3 = new Traitement();
			tr3.setGeneratedby(u.getUserName());
			tr3.setNote(note);
			tr3.setEtat(etat);
			trr.save(tr3);
			mail.setTr3(tr3);
				mr.save(mail);
		}
		return mail;
		
	}
	
}

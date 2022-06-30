package com.huytmb.mail.receiver.service;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class FinalMailSend implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) {
		
		System.out.println("final");
		
	}

}
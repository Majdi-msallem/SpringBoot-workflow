package com.huytmb.mail.receiver.service;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

public class MailRejectionService implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) {
		
		System.out.println("refus");
		
	}

}
package com.huytmb.mail.receiver.service;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huytmb.mail.receiver.model.mailModel;
import com.huytmb.mail.receiver.repository.MailRepo;


@Component
public class MailRejectionService {

	@Autowired
	private MailRepo mr;
	
	public String sendRejectmail(DelegateExecution execution) throws AddressException, MessagingException, IOException {
		   Properties props = new Properties();
		   props.put("mail.smtp.auth", "true");
		   props.put("mail.smtp.starttls.enable", "true");
		   props.put("mail.smtp.host", "smtp.gmail.com");
		   props.put("mail.smtp.port", "587");
		 
		   Session session = Session.getInstance(props, new javax.mail.Authenticator() {
		      protected PasswordAuthentication getPasswordAuthentication() {
		         return new PasswordAuthentication("testrh022@gmail.com", "ztygkzhlotfhfhae");
		      }
		   });
		   Message msg = new MimeMessage(session);
		   msg.setFrom(new InternetAddress("testrh022@gmail.com", false));
		    int idMail = (int) execution.getVariable("idMail");
			mailModel mail = mr.findById(idMail).orElse(null);
			System.out.println("iddddddddddddddd  adrees email pour envoyer"+idMail);
			System.out.println("adrees email pour envoyer"+mail);

		   msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getSenderAddress()));
		   msg.setSubject("refuser demande");
		   msg.setContent("refuser malheuresement", "text/html");
		   msg.setSentDate(new Date()); 

		  /* MimeBodyPart messageBodyPart = new MimeBodyPart();
		   messageBodyPart.setContent("Tutorials point email", "text/html");

		   Multipart multipart = new MimeMultipart();
		   multipart.addBodyPart(messageBodyPart);
		   MimeBodyPart attachPart = new MimeBodyPart();

		   attachPart.attachFile("/var/tmp/image19.png");
		   multipart.addBodyPart(attachPart);
		   msg.setContent(multipart);*/
		   Transport.send(msg);
		   
		return "Mail send successsfullyyyyy" ;   
		}
	public String sendacceptrhMail(DelegateExecution execution) throws AddressException, MessagingException, IOException {
		   Properties props = new Properties();
		   props.put("mail.smtp.auth", "true");
		   props.put("mail.smtp.starttls.enable", "true");
		   props.put("mail.smtp.host", "smtp.gmail.com");
		   props.put("mail.smtp.port", "587");
		 
		   Session session = Session.getInstance(props, new javax.mail.Authenticator() {
		      protected PasswordAuthentication getPasswordAuthentication() {
		         return new PasswordAuthentication("testrh022@gmail.com", "ztygkzhlotfhfhae");
		      }
		   });
		   Message msg = new MimeMessage(session);
		   msg.setFrom(new InternetAddress("testrh022@gmail.com", false));
		    int idMail = (int) execution.getVariable("idMail");
			mailModel mail = mr.findById(idMail).orElse(null);
			System.out.println("iddddddddddddddd  adrees email pour envoyer"+idMail); 
			System.out.println("adrees email pour envoyer"+mail);

		   msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getSenderAddress()));
		   msg.setSubject("accpter demande rh");
		   msg.setContent("accepter  et entretien  "+execution.getVariable("meet"), "text/html");
		   
		   msg.setSentDate(new Date());
		   Transport.send(msg);
		   
		return "Mail send successsfullyyyyy" ;   
		}
	public String sendacceptdrhMail(DelegateExecution execution) throws AddressException, MessagingException, IOException {
		   Properties props = new Properties();
		   props.put("mail.smtp.auth", "true");
		   props.put("mail.smtp.starttls.enable", "true");
		   props.put("mail.smtp.host", "smtp.gmail.com");
		   props.put("mail.smtp.port", "587");
		 
		   Session session = Session.getInstance(props, new javax.mail.Authenticator() {
		      protected PasswordAuthentication getPasswordAuthentication() {
		         return new PasswordAuthentication("testrh022@gmail.com", "ztygkzhlotfhfhae");
		      }
		   });
		   Message msg = new MimeMessage(session);
		   msg.setFrom(new InternetAddress("testrh022@gmail.com", false));
		    int idMail = (int) execution.getVariable("idMail");
			mailModel mail = mr.findById(idMail).orElse(null);
			System.out.println("iddddddddddddddd  adrees email pour envoyer"+idMail); 
			System.out.println("adrees email pour envoyer"+mail);

		   msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getSenderAddress()));
		   msg.setSubject("accpter demande par drh");
		   msg.setContent("accepter  "+execution.getVariable("meet"), "text/html");
		   
		   msg.setSentDate(new Date());
		   Transport.send(msg); 
		   
		return "Mail send successsfullyyyyy" ;   
		}
	public String sendrefusdrhMail(DelegateExecution execution) throws AddressException, MessagingException, IOException {
		   Properties props = new Properties();
		   props.put("mail.smtp.auth", "true");
		   props.put("mail.smtp.starttls.enable", "true");
		   props.put("mail.smtp.host", "smtp.gmail.com");
		   props.put("mail.smtp.port", "587");
		 
		   Session session = Session.getInstance(props, new javax.mail.Authenticator() {
		      protected PasswordAuthentication getPasswordAuthentication() {
		         return new PasswordAuthentication("testrh022@gmail.com", "ztygkzhlotfhfhae");
		      }
		   });
		   Message msg = new MimeMessage(session);
		   msg.setFrom(new InternetAddress("testrh022@gmail.com", false));
		    int idMail = (int) execution.getVariable("idMail");
			mailModel mail = mr.findById(idMail).orElse(null);
			System.out.println("iddddddddddddddd  adrees email pour envoyer"+idMail); 
			System.out.println("adrees email pour envoyer"+mail);

		   msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getSenderAddress()));
		   msg.setSubject("refuser demande par drh");
		   msg.setContent("refuser malheuresement ", "text/html");
		   
		   msg.setSentDate(new Date());
		   Transport.send(msg);
		   
		return "Mail send successsfullyyyyy" ;   
		} 
}
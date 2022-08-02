package com.huytmb.mail.receiver.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
	/*@Autowired 
	JavaMailSender jms;*/
	
	
	
	public Page<mailModel> getAllMail(PageRequest pr,String recherche){
		
		if (recherche.equals(""))	
			  return (Page<mailModel>) mr.findAll(pr);
					 List<mailModel> mails= mr.findAll().stream()
						      .filter(mail -> mail.getSenderAddress().contains(recherche) || mail.getSubject().contains(recherche))
						      .collect(Collectors.toList());
					 int start = (int) pr.getOffset();
					 int end = (int) ((start + pr.getPageSize()) > mails.size() ? mails.size()
							   : (start + pr.getPageSize()));
					 Page<mailModel> allmailpage = new PageImpl<>(mails.subList(start, end),pr,mails.size());
					 return allmailpage;
	}
	
	public Optional<mailModel> getMailByID(int id){
		return mr.findById(id);
	}
	
	public Optional<mailModel> refuser_Condidature(HttpServletRequest request,int idMail,boolean fs){
		Optional<mailModel> mail=getMailByID(idMail);		
		return null;
	}

	public Page<mailModel> encours(PageRequest pr,String recherche) {
		if (recherche.equals(""))	
			  return (Page<mailModel>) mr.findAll(pr);
					 List<mailModel> mails= mr.findAll().stream()
						      .filter(mail -> mail.getSenderAddress().contains(recherche) || mail.getSubject().contains(recherche))
						      .collect(Collectors.toList());
					 int start = (int) pr.getOffset();
					 int end = (int) ((start + pr.getPageSize()) > mails.size() ? mails.size()
							   : (start + pr.getPageSize()));
					 Page<mailModel> allmailpage = new PageImpl<>(mails.subList(start, end),pr,mails.size());
					 return allmailpage;
	}
	public Page<mailModel> nontraiter(PageRequest pr,String recherche) {
		if (recherche.equals(""))	
			  return (Page<mailModel>) mr.findAll(pr);
					 List<mailModel> mails= mr.findAll().stream()
						      .filter(mail -> mail.getSenderAddress().contains(recherche) || mail.getSubject().contains(recherche))
						      .collect(Collectors.toList());
					 int start = (int) pr.getOffset();
					 int end = (int) ((start + pr.getPageSize()) > mails.size() ? mails.size()
							   : (start + pr.getPageSize()));
					 Page<mailModel> allmailpage = new PageImpl<>(mails.subList(start, end),pr,mails.size());
					 return allmailpage;
	}
	public Page<mailModel> traiter(PageRequest pr,String recherche) {
		if (recherche.equals(""))	
			  return (Page<mailModel>) mr.findAll(pr);
					 List<mailModel> mails= mr.findAll().stream()
						      .filter(mail -> mail.getSenderAddress().contains(recherche) || mail.getSubject().contains(recherche))
						      .collect(Collectors.toList());
					 int start = (int) pr.getOffset();
					 int end = (int) ((start + pr.getPageSize()) > mails.size() ? mails.size()
							   : (start + pr.getPageSize()));
					 Page<mailModel> allmailpage = new PageImpl<>(mails.subList(start, end),pr,mails.size());
					 return allmailpage;
	}
	public Page<mailModel> tr1(PageRequest pr,String recherche) {
		if (recherche.equals(""))	
			return mr.tr1(pr);
		List<mailModel> mails= mr.findAll().stream()
						      .filter(mail -> mail.getSenderAddress().contains(recherche) || mail.getSubject().contains(recherche))
						      .collect(Collectors.toList());
					 int start = (int) pr.getOffset();
					 int end = (int) ((start + pr.getPageSize()) > mails.size() ? mails.size()
							   : (start + pr.getPageSize()));
					 Page<mailModel> allmailpage = new PageImpl<>(mails.subList(start, end),pr,mails.size());
					 return allmailpage;
	}
	public Page<mailModel> tr2(PageRequest pr,String recherche) {
		if (recherche.equals(""))	
			return mr.tr2(pr);
		List<mailModel> mails= mr.findAll().stream()
						      .filter(mail -> mail.getSenderAddress().contains(recherche) || mail.getSubject().contains(recherche))
						      .collect(Collectors.toList());
					 int start = (int) pr.getOffset();
					 int end = (int) ((start + pr.getPageSize()) > mails.size() ? mails.size()
							   : (start + pr.getPageSize()));
					 Page<mailModel> allmailpage = new PageImpl<>(mails.subList(start, end),pr,mails.size());
					 return allmailpage;	
					 }
	public Page<mailModel> ListeDesEmailGenererTR1(String generatedby,PageRequest pr,String recherche) {
		if (recherche.equals(""))	
			return mr.MailGeneratedBy(generatedby,pr);
					 List<mailModel> mails= mr.findAll().stream()
						      .filter(mail -> mail.getSenderAddress().contains(recherche) || mail.getSubject().contains(recherche))
						      .collect(Collectors.toList());
					 int start = (int) pr.getOffset();
					 int end = (int) ((start + pr.getPageSize()) > mails.size() ? mails.size()
							   : (start + pr.getPageSize()));
					 Page<mailModel> allmailpage = new PageImpl<>(mails.subList(start, end),pr,mails.size());
					 return allmailpage;
	}
	public Page<mailModel> MailsListeTR1ByUserName(String generatedby,PageRequest pr,String recherche) {
		if (recherche.equals(""))	
			return mr.MailTR1ByUserName(generatedby,pr);
					 List<mailModel> mails= mr.findAll().stream()
						      .filter(mail -> mail.getSenderAddress().contains(recherche) || mail.getSubject().contains(recherche))
						      .collect(Collectors.toList());
					 int start = (int) pr.getOffset();
					 int end = (int) ((start + pr.getPageSize()) > mails.size() ? mails.size()
							   : (start + pr.getPageSize()));
					 Page<mailModel> allmailpage = new PageImpl<>(mails.subList(start, end),pr,mails.size());
					 return allmailpage;
	}
	
	public String sendmail() throws AddressException, MessagingException, IOException {
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

		   msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("testrh022@gmail.com"));
		   msg.setSubject("Tutorials point email");
		   msg.setContent("Tutorials point email", "text/html");
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
}

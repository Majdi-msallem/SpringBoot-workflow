package com.huytmb.mail.receiver.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.huytmb.mail.receiver.model.Status;
import com.huytmb.mail.receiver.model.attachementsModel;
import com.huytmb.mail.receiver.model.mailModel;
import com.huytmb.mail.receiver.service.MailService;
import com.huytmb.mail.receiver.service.ReceiveMailServiceImpl;
@RestController
public class MailController {
	
	@Autowired
	private MailService ms;
	@Autowired
	private ReceiveMailServiceImpl rms;
	
	@GetMapping("/sendEmail")
	public String sendEmail() throws AddressException, MessagingException, IOException{
		return ms.sendmail();
	}
	
	
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
	
	@GetMapping("/mailsencours")
	@ResponseBody
	public List<mailModel> MailsEncours() {
		return ms.encours();
	}
	@GetMapping("/mailsnontraiter")
	@ResponseBody
	public List<mailModel> Mailsnontraiter() {
		return ms.nontraiter();
	}
	@GetMapping("/mailstraiter")
	@ResponseBody
	public List<mailModel> Mailstraiter() {
		return ms.traiter();
	}
	@GetMapping("/tr1")
	@ResponseBody
	public List<mailModel> rhtr() {
		return ms.tr1();
	}
	@GetMapping("/tr2")
	@ResponseBody
	public List<mailModel> techtr() {
		return ms.tr2();
	}
	@GetMapping("/listeMails/{generatedby}")
	@ResponseBody
	public List<mailModel> listeMaylgenererPar(@PathVariable String generatedby) {
		return ms.ListeDesEmailGenererTR1(generatedby);
	}
	
	
	
	@GetMapping(value = "/src/{idMail}/{idAtt}", produces = MediaType.ALL_VALUE)
    ResponseEntity<FileSystemResource> downloadFile(@PathVariable("idMail") int idMail,@PathVariable("idAtt") int idAtt) {
		attachementsModel attachment=rms.getAttachmentsByMailID(idMail, idAtt);
        File file = new File(attachment.getName());
        String nameOfFile= attachment.getName().split("\\\\")[attachment.getName().split("\\\\").length-1];
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + nameOfFile + "\"")
                .contentType(MediaType.valueOf("application/pdf")).body(new FileSystemResource(file));
    }
	
	
}

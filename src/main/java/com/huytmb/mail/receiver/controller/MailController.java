package com.huytmb.mail.receiver.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.huytmb.mail.receiver.model.Status;
import com.huytmb.mail.receiver.model.attachementsModel;
import com.huytmb.mail.receiver.model.mailModel;
import com.huytmb.mail.receiver.service.MailService;
import com.huytmb.mail.receiver.service.ReceiveMailServiceImpl;
import com.huytmb.mail.receiver.util.JwtUtil;
@RestController
public class MailController {
	
	@Autowired
	private MailService ms;
	@Autowired
	private ReceiveMailServiceImpl rms;
	@Autowired
	private JwtUtil ju;
	
	@GetMapping("/sendEmail")
	public String sendEmail() throws AddressException, MessagingException, IOException{
		return ms.sendmail();
	}
	
	
	@GetMapping("/getAllMail")
	@ResponseBody
	public Page<mailModel> getalldemande(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,@RequestParam(required = false)String recherche)
	{
		 PageRequest pr=PageRequest.of(page, size);
		 	return ms.getAllMail(pr,recherche) ;
	}
	
	@GetMapping("/getMailById/{id}")
	@ResponseBody
	public Optional<mailModel> GetMailById(@PathVariable int id){
		return ms.getMailByID(id);
	}
	
	@GetMapping("/mailsencours")
	@ResponseBody
	public Page<mailModel> MailsEncours(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,@RequestParam(required = false)String recherche) {
		 PageRequest pr=PageRequest.of(page, size);
		return ms.encours(pr,recherche);
	}
	@GetMapping("/mailsnontraiter")
	@ResponseBody
	public Page<mailModel> Mailsnontraiter(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,@RequestParam(required = false)String recherche) {
		 PageRequest pr=PageRequest.of(page, size);
		return ms.nontraiter(pr,recherche);
	}
	@GetMapping("/mailstraiter")
	@ResponseBody
	public Page<mailModel> Mailstraiter(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,@RequestParam(required = false)String recherche) {
		 PageRequest pr=PageRequest.of(page, size);
		 return ms.traiter(pr,recherche);
	}
	@GetMapping("/tr1")
	@ResponseBody
	public Page<mailModel> rhtr(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,@RequestParam(required = false)String recherche) {
		 PageRequest pr=PageRequest.of(page, size);
		 	return ms.tr1(pr,recherche);
	}
	@GetMapping("/tr2")
	@ResponseBody
	public Page<mailModel> techtr(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,@RequestParam(required = false)String recherche) {
		 PageRequest pr=PageRequest.of(page, size);
		 	return ms.tr2(pr,recherche);
	}
	
	/*  */
	@GetMapping("/listeMailstr2")
	@ResponseBody
	public Page<mailModel> listeMaylgenererPar( String generatedby,HttpServletRequest request,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,@RequestParam(required = false)String recherche) {
		generatedby = ju.getuserFromRequest(request).getUserName(); 
		PageRequest pr=PageRequest.of(page, size);
		return ms.ListeDesEmailGenererTR2(generatedby,pr,recherche);
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
	
	@GetMapping("/listeMailsGeneratedByName")
	@ResponseBody
	public Page<mailModel> listeMaylgenererPar(HttpServletRequest request, String generatedby,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,@RequestParam(required = false)String recherche) {
		generatedby = ju.getuserFromRequest(request).getUserName();
		 PageRequest pr=PageRequest.of(page, size);
		return ms.MailsListeTR1ByUserName(generatedby,pr,recherche);
	}
	
}

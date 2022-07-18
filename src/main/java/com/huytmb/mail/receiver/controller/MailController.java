package com.huytmb.mail.receiver.controller;

import java.io.File;
import java.util.List;
import java.util.Optional;

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
	@GetMapping(value = "/src/{idMail}/{idAtt}", produces = MediaType.ALL_VALUE)
    ResponseEntity<FileSystemResource> downloadFile(@PathVariable("idMail") int idMail,@PathVariable("idAtt") int idAtt) {
		attachementsModel attachment=rms.getAttachmentsByMailID(idMail, idAtt);
        File file = new File(attachment.getName());
        String nameOfFile= attachment.getName().split("\\\\")[attachment.getName().split("\\\\").length-1];
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nameOfFile + "\"")
                .contentType(MediaType.valueOf("application/pdf")).body(new FileSystemResource(file));
    }
}

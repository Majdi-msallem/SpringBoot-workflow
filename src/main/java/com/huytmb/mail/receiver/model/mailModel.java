package com.huytmb.mail.receiver.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class mailModel {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int  idMail;
	 private String subject;
	    private String senderAddress;
	    private String cc;
	    private String contentType;
	    
		@Enumerated(EnumType.STRING)
	    private Status Status;
		
		@ManyToOne(cascade =CascadeType.PERSIST)
		private Traitement tr1;
		
		@ManyToOne(cascade =CascadeType.PERSIST)
		private Traitement tr2;
		
		
		
	    @OneToMany(cascade=CascadeType.ALL,mappedBy="mailmodel")
	    private List<attachementsModel> attachments = new ArrayList<attachementsModel>();
	    
	    
	    
	    
	
	    
		
	    
	    
	
		
	    
	    
	    
	    
}

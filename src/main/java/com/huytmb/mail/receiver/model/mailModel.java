package com.huytmb.mail.receiver.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


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
	private int  id;
	 private String subject;
	    private String senderAddress;
	    private String cc;
	    private String contentType;
	    
	   
	    @OneToMany(cascade=CascadeType.ALL,mappedBy="mailmodel")
	    private List<attachementsModel> attachments = new ArrayList<attachementsModel>();
	    
	    
	    
	    
	
	    
		
	    
	    
	
		
	    
	    
	    
	    
}

package com.huytmb.mail.receiver.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
public class attachementsModel {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int  id;
	 private String name;
	
	 
	 @ManyToOne()
	 private mailModel mailmodel;


	public attachementsModel(String name) {
		super();
		this.name = name;
	}


	public attachementsModel(String name, mailModel mailmodel) {
		super();
		this.name = name;
		this.mailmodel = mailmodel;
	}
	 
	


}

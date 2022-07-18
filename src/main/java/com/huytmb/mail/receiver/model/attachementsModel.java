package com.huytmb.mail.receiver.model;


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


@Entity
public class attachementsModel {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int  idAtt;
	 private String name;
	
	 @JsonIgnore
	 @ManyToOne()
	 private mailModel mailmodel;


	public attachementsModel() {
	}


	public attachementsModel(String name) {
		this.name = name;
	}


	public attachementsModel(String name, mailModel mailmodel) {
		super();
		this.name = name;
		this.mailmodel = mailmodel;
	}


	public int getIdAtt() {
		return idAtt;
	}


	public void setIdAtt(int idAtt) {
		this.idAtt = idAtt;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public mailModel getMailmodel() {
		return mailmodel;
	}


	public void setMailmodel(mailModel mailmodel) {
		this.mailmodel = mailmodel;
	}


	@Override
	public String toString() {
		return "attachementsModel [idAtt=" + idAtt + ", name=" + name + ", mailmodel=" + mailmodel + "]";
	}
	 
	


}

package com.huytmb.mail.receiver.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
	    @Column(columnDefinition = "LONGTEXT")
	    private String cc;
	   private String meet;
	    
	    
		@Enumerated(EnumType.STRING)
	    private Status Status;
		

		@ManyToOne(cascade =CascadeType.PERSIST)
		private Traitement tr1;
		
		@ManyToOne(cascade =CascadeType.PERSIST)
		private Traitement tr2;
		
		@ManyToOne(cascade =CascadeType.PERSIST)
		private Traitement tr3;
		
		
		
	    @OneToMany(cascade=CascadeType.ALL,mappedBy="mailmodel",fetch=FetchType.EAGER)
	    private List<attachementsModel> attachments = new ArrayList<attachementsModel>();



		public int getIdMail() {
			return idMail;
		}



		public void setIdMail(int idMail) {
			this.idMail = idMail;
		}



		public String getSubject() {
			return subject;
		}



		public void setSubject(String subject) {
			this.subject = subject;
		}



		public String getSenderAddress() {
			return senderAddress;
		}



		public void setSenderAddress(String senderAddress) {
			this.senderAddress = senderAddress;
		}



		public String getCc() {
			return cc;
		}



		public void setCc(String cc) {
			this.cc = cc;
		}

		public Status getStatus() {
			return Status;
		}



		public void setStatus(Status status) {
			Status = status;
		}


		public Traitement getTr1() {
			return tr1;
		}



		public void setTr1(Traitement tr1) {
			this.tr1 = tr1;
		}



		public Traitement getTr2() {
			return tr2;
		}



		public void setTr2(Traitement tr2) {
			this.tr2 = tr2;
		}



		public List<attachementsModel> getAttachments() {
			return attachments;
		}



		public void setAttachments(List<attachementsModel> attachments) {
			this.attachments = attachments;
		}

		
		public Traitement getTr3() {
			return tr3;
		}



		public void setTr3(Traitement tr3) {
			this.tr3 = tr3;
		}



		public String getMeet() {
			return meet;
		}



		public void setMeet(String meet) {
			this.meet = meet;
		}
		
	    
	    
	    
	    
	    
	    
	    
	
	    
		
	    
	    
	
		
	    
	    
	    
	    
}

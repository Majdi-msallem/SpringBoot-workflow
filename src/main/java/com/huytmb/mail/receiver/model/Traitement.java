package com.huytmb.mail.receiver.model;



import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class Traitement {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int trId;
	
	private String note;
	private String generatedby;
	
	@Enumerated(EnumType.STRING)
	private Etat etat;
	
	@JsonIgnore
	@OneToMany(mappedBy="tr1",cascade= CascadeType.ALL)
	private List<mailModel> Listmail1 ;
	
	@JsonIgnore
	@OneToMany(mappedBy="tr2",cascade= CascadeType.ALL)
	private List<mailModel> Listmail2 ;
	
	@JsonIgnore
	@OneToMany(mappedBy="tr3",cascade= CascadeType.ALL)
	private List<mailModel> Listmail3 ;
	
	

}

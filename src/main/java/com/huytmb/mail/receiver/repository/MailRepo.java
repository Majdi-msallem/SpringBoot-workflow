package com.huytmb.mail.receiver.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.huytmb.mail.receiver.model.mailModel;

@Repository
public interface MailRepo extends CrudRepository<mailModel, Integer> {

	
}

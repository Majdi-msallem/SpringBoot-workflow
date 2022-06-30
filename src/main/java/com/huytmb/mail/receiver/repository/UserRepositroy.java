package com.huytmb.mail.receiver.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.huytmb.mail.receiver.model.User;

@Repository
public interface UserRepositroy extends CrudRepository<User, Integer> {
	User findByUserName(String userName);

}

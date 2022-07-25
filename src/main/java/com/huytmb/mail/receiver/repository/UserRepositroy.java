package com.huytmb.mail.receiver.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.huytmb.mail.receiver.model.User;

@Repository
public interface UserRepositroy extends JpaRepository<User, Integer> {
	User findByUserName(String userName);
	
	@Query("SELECT u FROM User u JOIN u.role ur WHERE ur.roleName = 'tech'")
	List<User> findAllUsersByroleName(); 
	
	@Query("UPDATE User u SET u.enabled = true WHERE u.id = ?1")
	@Modifying
	@Transactional
	public void enable(Integer id);
	
	@Query("Select u FROM User u WHERE u.verificationcode = ?1")
	public User FindByVerificationCode(String Code);
	
}

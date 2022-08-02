package com.huytmb.mail.receiver.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.huytmb.mail.receiver.model.Status;
import com.huytmb.mail.receiver.model.mailModel;

@Repository
public interface MailRepo extends JpaRepository<mailModel, Integer> {
	    @Query("SELECT m FROM mailModel m WHERE m.Status = 'encours'")
        List<mailModel> encours();  
        @Query("SELECT m FROM mailModel m WHERE m.tr1 != null ")
        Page<mailModel> tr1(PageRequest pr); 
        @Query("SELECT m FROM mailModel m WHERE m.tr2 != null ")
        Page<mailModel> tr2(PageRequest pr); 
         
        @Query("SELECT m FROM mailModel m WHERE m.Status = 'nontraiter'")
        List<mailModel> nontraiter();  
        
        @Query("SELECT m FROM mailModel m WHERE m.Status = 'traiter'")
        List<mailModel> traiter();  
        
        @Query("SELECT m FROM mailModel m WHERE m.tr2.generatedby = ?1")
        Page<mailModel> MailGeneratedBy( String generatedby,PageRequest pr);
        
        @Query("SELECT m FROM mailModel m WHERE m.tr1.generatedby = ?1")
        Page<mailModel> MailTR1ByUserName( String generatedby,PageRequest pr);
}
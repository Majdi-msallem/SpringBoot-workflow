package com.huytmb.mail.receiver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.huytmb.mail.receiver.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
	Role findByRoleName(String roleName);

}
 
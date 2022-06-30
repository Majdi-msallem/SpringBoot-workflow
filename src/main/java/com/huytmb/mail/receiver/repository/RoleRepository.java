package com.huytmb.mail.receiver.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.huytmb.mail.receiver.model.Role;
import com.huytmb.mail.receiver.model.User;

@Repository
public interface RoleRepository extends CrudRepository<Role,Integer> {
	Role findByRoleName(String roleName);

}

package com.ctem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ctem.entity.AuthorizationRole;


@Repository
public interface AuthorizationRoleRepository extends JpaRepository<AuthorizationRole, Long> {
	
	/*
	public static final String SELECT_QUERY = " SELECT msg.name FROM FileMessages msg where msg.code = :status";
	
	@Query(SELECT_QUERY)
	String getFileMessage(@Param("status") String status);
	*/
}

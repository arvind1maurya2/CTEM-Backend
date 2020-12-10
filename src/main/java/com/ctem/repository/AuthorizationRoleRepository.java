package com.ctem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ctem.entity.AuthorizationRole;


@Repository
public interface AuthorizationRoleRepository extends JpaRepository<AuthorizationRole, Long> {

}

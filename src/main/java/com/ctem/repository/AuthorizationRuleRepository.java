package com.ctem.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ctem.entity.AuthorizationRule;

@Repository
public interface AuthorizationRuleRepository extends JpaRepository<AuthorizationRule, Long> {

	//	@Query("SELECT sbm FROM AuthorizationRule sbm WHERE sbm.parent = true")
	//	List<AuthorizationRule> findAllParentMenus();
	//	@Query("SELECT sbm FROM AuthorizationRule sbm WHERE sbm.parentId = :parentId")
	//	List<AuthorizationRule> findAllChildMenus(@Param("parentId") Long parentId);

}

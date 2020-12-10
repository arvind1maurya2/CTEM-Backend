package com.ctem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ctem.entity.Office;

@Repository
public interface OfficeRepository extends JpaRepository<Office, Long>{
	
	boolean existsByName(String code);
	
	List<Office> findByArchived(boolean archived);

}

package com.ctem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ctem.entity.Office;

@Repository
public interface OfficeRepository extends JpaRepository<Office, Long>{

}

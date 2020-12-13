package com.ctem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ctem.entity.Division;

@Repository
public interface DivisionRepository extends JpaRepository<Division, Long>{

}

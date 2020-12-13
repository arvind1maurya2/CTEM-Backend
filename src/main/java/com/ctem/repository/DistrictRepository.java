package com.ctem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ctem.entity.District;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long>{

}

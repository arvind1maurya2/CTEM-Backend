package com.ctem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ctem.entity.City;

public interface CityRepository extends JpaRepository<City, Long> {

}
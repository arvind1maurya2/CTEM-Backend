package com.ctem.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.ctem.entity.Role;

/**
 * @author Shashank
 *
 */
@Service
public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(String roleName);
}
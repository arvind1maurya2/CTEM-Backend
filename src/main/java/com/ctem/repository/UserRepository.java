package com.ctem.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.ctem.entity.UserEntity;

/**
 * @author Shashank
 *
 */
@Service
public interface UserRepository extends JpaRepository<UserEntity, Long>{
	
	Optional<UserEntity> findByEmail(String email);

	UserEntity findByUsernameOrEmail(String username, String email);

	List<UserEntity> findByIdIn(List<Long> userIds);

	Optional<UserEntity> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);


}

package com.mydiary.api.users.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsersRepository extends JpaRepository<UserEntity, String> {
	
	UserEntity findByEmail(String email);
	
	UserEntity findByUserId(String userId);
	
	@Query(value = "SELECT * FROM users WHERE MATCH (first_name, last_name, email) AGAINST (?1)", nativeQuery = true)
	List<UserEntity> searchUser(String keyword);

}

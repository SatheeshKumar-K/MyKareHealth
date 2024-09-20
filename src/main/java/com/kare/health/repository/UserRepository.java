package com.kare.health.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kare.health.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query(nativeQuery = true, value = "select * from user where email=:email")
	User findByUserName(String email);

	@Query(nativeQuery = true, value = "select * from user where email=:email")
	Optional<User> findByEmail(String email);

	@Query(nativeQuery = true, value = "select * from user where user_id=:id")
	Optional<User> findByUserId(Long id);

	//delete the user using email
	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "DELETE FROM user WHERE email=:email")
	void deleteByEmail(String email);

}

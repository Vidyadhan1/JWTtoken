package com.farmeco.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.farmeco.entity.User;

public interface UserRepository extends JpaRepository<User,Integer> {

	Boolean existsByEmail(String email);
	
	List<User> findAllByIsDeletedFalse();
	
	Optional<User> findByEmail(String email);
}

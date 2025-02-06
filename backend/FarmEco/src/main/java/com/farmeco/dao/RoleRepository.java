package com.farmeco.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.farmeco.entity.Role;

public interface RoleRepository extends JpaRepository<Role,Integer> {

}

package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.query.Param;

import com.example.demo.models.User;


public interface UserRepository extends JpaRepository<User, Integer>{
	
	public User findByEmail(String email);
	

}

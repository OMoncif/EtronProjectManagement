package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.Param;

import com.example.demo.models.User;


public interface UserRepository extends JpaRepository<User, Integer>{
	
	@Query("select u from User u where u.email = :email")
	User findByEmail(@Param("email") String email);
	
	@Query("select u from User u where u.id = :idUser")
	User findById(@Param("idUser") int idUser);
	

}

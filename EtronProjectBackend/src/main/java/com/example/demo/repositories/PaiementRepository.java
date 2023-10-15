package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.models.Paiement;

public interface PaiementRepository extends JpaRepository<Paiement, Integer>{

	@Query("select count(*) from Paiement p where p.user.email= :email")
	int getPaiementCountByUserName(@Param("email") String email);
}

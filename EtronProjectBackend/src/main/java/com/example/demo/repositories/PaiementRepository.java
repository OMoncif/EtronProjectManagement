package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.models.Paiement;

public interface PaiementRepository extends JpaRepository<Paiement, Integer>{

	@Query("select count(*) from Paiement p where p.user.email= :email")
	int getPaiementCountByUserName(@Param("email") String email);
	
	@Query("select p from Paiement p where p.user.email = :email")
	public List<Paiement> getPaiementsByUsername(@Param("email") String email);
	
	@Query("SELECT p FROM Paiement p WHERE p.user.id = :idUser AND MONTH(p.datePaiement) = :mois")
    public Paiement getPaiementByUserAndMonth(@Param("idUser") int idUser, @Param("mois") int mois);
}

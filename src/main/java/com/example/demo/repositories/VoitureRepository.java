package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.models.User;
import com.example.demo.models.Voiture;

public interface VoitureRepository extends JpaRepository<Voiture, Integer>{

	@Query("select v from Voiture v where v.user = :user")
	public Voiture findVoitureByUser(@Param("user") User user);
	
	Voiture findByModele(@Param("modele") String modele);
	
	@Query("select max(v.modele) from Voiture v")
	public String getDernierModele();
}

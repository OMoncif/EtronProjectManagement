package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.models.Facture;
import com.example.demo.models.User;

public interface FactureRepository extends JpaRepository<Facture, Integer>{
	
	@Query("select f from Facture f where f.contrat.user = :user AND MONTH(f.dateFacturation) = :mois AND YEAR(f.dateFacturation) = :annee")
	Facture getFactureByUserAndAnneeAndMois(@Param("user") User user,@Param("mois") int mois , @Param("annee") int annee);
	
	@Query("select count(*) from Facture f where f.contrat.user.email= :email")
	int getFactureCountByUserName(@Param("email") String email);
	
	@Query("select f from Facture f where f.contrat.user.email = :email")
	public List<Facture> getFacturesByUsername(@Param("email") String email);

}

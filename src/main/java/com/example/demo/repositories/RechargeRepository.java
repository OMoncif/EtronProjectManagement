package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.models.Recharge;
import com.example.demo.models.User;

public interface RechargeRepository extends JpaRepository<Recharge,Long> {
	
	@Query("SELECT r FROM Recharge r WHERE r.user = :user AND MONTH(r.dateHeureRecharge) = :mois AND YEAR(r.dateHeureRecharge) = :annee")
	public List<Recharge> findByUserAndMoisAndAnnee(@Param("user") User user , @Param("mois") int mois, @Param("annee") int annee);

}

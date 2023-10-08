package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.models.AbonnementPlan;

public interface AbonnementPlanRepository extends JpaRepository<AbonnementPlan, Integer>{
	
	@Query("select a from AbonnementPlan a where a.type = :type")
	public AbonnementPlan findByType(@Param("type") String type);
}

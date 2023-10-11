package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.models.BorneRecharge;

public interface BorneRechargeRepository extends JpaRepository<BorneRecharge, Integer>{
	
	@Query("SELECT b FROM BorneRecharge b WHERE b.latitude = :latitude AND b.longitude = :longitude")
    BorneRecharge getBorneByLongitudeAndLatitude(@Param("latitude") double latitude, @Param("longitude") double longitude);

}

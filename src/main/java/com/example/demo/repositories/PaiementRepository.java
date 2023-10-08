package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Paiement;

public interface PaiementRepository extends JpaRepository<Paiement, Integer>{

}

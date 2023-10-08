package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Facture;

public interface FactureRepository extends JpaRepository<Facture, Integer>{

}

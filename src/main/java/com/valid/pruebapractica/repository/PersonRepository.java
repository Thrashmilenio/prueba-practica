package com.valid.pruebapractica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.valid.pruebapractica.model.Personas;

@Repository
public interface PersonRepository extends JpaRepository<Personas, Long>{

}

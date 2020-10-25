package com.valid.pruebapractica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.valid.pruebapractica.model.Personas;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PersonRepository extends JpaRepository<Personas, Long>{

    @Transactional
    @Modifying
    @Query(value = "update personas set procesado = 'true'", nativeQuery=true)
    public void updateProcess();

}

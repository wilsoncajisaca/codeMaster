package com.practica.examen.CodeMaster.repositories;

import com.practica.examen.CodeMaster.entities.TipoPersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITipoPersonaRepository extends JpaRepository<TipoPersonaEntity, Long> {
    List<TipoPersonaEntity> findByEstadoTrue();
}
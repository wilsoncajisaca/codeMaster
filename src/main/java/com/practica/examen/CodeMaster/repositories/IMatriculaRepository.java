package com.practica.examen.CodeMaster.repositories;

import com.practica.examen.CodeMaster.entities.MatriculaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMatriculaRepository extends JpaRepository<MatriculaEntity, Long> {
    Integer countByGrupoIdGrupoAndEstadoTrue(Long idGrupo);
    List<MatriculaEntity> findByEstadoTrueAndPersona_IdPersona(Long idPersona);
}
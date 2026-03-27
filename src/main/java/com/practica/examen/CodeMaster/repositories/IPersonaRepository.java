package com.practica.examen.CodeMaster.repositories;

import com.practica.examen.CodeMaster.entities.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPersonaRepository extends JpaRepository<PersonaEntity, Long> {
    List<PersonaEntity> findByEstadoTrue();

    List<PersonaEntity> findByTipoPersonaCodigo(String codigo);

    Boolean existsByIdentificacion(String identificacion);

    Optional<PersonaEntity> findByIdPersonaAndTipoPersonaCodigo(Long id, String codigo);
}

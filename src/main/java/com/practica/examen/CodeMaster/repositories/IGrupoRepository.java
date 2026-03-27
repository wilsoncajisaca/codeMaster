package com.practica.examen.CodeMaster.repositories;

import com.practica.examen.CodeMaster.entities.GrupoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGrupoRepository extends JpaRepository<GrupoEntity, Long> {
}

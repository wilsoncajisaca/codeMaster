package com.practica.examen.CodeMaster.repositories;

import com.practica.examen.CodeMaster.entities.CursoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICursoRepository extends JpaRepository<CursoEntity, Long> {
}

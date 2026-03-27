package com.practica.examen.CodeMaster.repositories;

import com.practica.examen.CodeMaster.entities.EvaluacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEvaluacionRepository extends JpaRepository<EvaluacionEntity, Long> {
}

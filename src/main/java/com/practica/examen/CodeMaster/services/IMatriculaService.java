package com.practica.examen.CodeMaster.services;

import com.practica.examen.CodeMaster.entities.MatriculaEntity;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface IMatriculaService {
    void guardar(MatriculaEntity entity);

    List<MatriculaEntity> listar();

    List<MatriculaEntity> obtenerPorEstudianteId(Long idPersona);
}

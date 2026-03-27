package com.practica.examen.CodeMaster.services;

import com.practica.examen.CodeMaster.entities.EvaluacionEntity;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface IEvaluacionService {
    void guardar(EvaluacionEntity entity);

    List<EvaluacionEntity> listar();
}

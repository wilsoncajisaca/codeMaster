package com.practica.examen.CodeMaster.services;

import com.practica.examen.CodeMaster.entities.CursoEntity;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface ICursoService {
    void guardar(CursoEntity entity);

    void actualizar(CursoEntity entity);

    List<CursoEntity> listar();
}

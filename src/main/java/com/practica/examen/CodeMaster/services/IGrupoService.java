package com.practica.examen.CodeMaster.services;

import com.practica.examen.CodeMaster.entities.GrupoEntity;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface IGrupoService {
    void guardar(GrupoEntity entity);

    List<GrupoEntity> listar();
}

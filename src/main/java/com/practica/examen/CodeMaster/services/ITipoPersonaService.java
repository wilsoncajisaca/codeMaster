package com.practica.examen.CodeMaster.services;

import com.practica.examen.CodeMaster.entities.TipoPersonaEntity;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface ITipoPersonaService {
    void guardar(TipoPersonaEntity entity);

    List<TipoPersonaEntity> listar();
}

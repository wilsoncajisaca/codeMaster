package com.practica.examen.CodeMaster.services;

import com.practica.examen.CodeMaster.entities.PersonaEntity;

import java.util.List;

public interface IPersonaService {
    void guardar(PersonaEntity entity);

    List<PersonaEntity> listar();

    List<PersonaEntity> listarDocentes();

    List<PersonaEntity> listarEstudiantes();

    void eliminarEstudiante(Long id);
}

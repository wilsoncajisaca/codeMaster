package com.practica.examen.CodeMaster.services.impl;

import com.practica.examen.CodeMaster.entities.PersonaEntity;
import com.practica.examen.CodeMaster.repositories.IPersonaRepository;
import com.practica.examen.CodeMaster.services.IMatriculaService;
import com.practica.examen.CodeMaster.services.IPersonaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonaService implements IPersonaService {
    private final IPersonaRepository repository;
    private final IMatriculaService matriculaService;
    private final String CODIGO_DOCENTE = "DOC";
    private final String CODIGO_ESTUDIANTE = "EST";

    @Override
    public void guardar(PersonaEntity estudianteEntity) {

        if(existByIdentificacion(estudianteEntity.getIdentificacion())){
            throw new RuntimeException("El usuario con la identificación " +
                    estudianteEntity.getIdentificacion() + " ya existe.");
        }

        this.setEdad(estudianteEntity);
        this.repository.save(estudianteEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonaEntity> listar() {
        return this.repository.findByEstadoTrue();
    }

    @Override
    public List<PersonaEntity> listarDocentes() {
        return this.repository.findByTipoPersonaCodigo(CODIGO_DOCENTE);
    }

    @Override
    public List<PersonaEntity> listarEstudiantes() {
        return this.repository.findByTipoPersonaCodigo(CODIGO_ESTUDIANTE);
    }

    @Override
    public void eliminarEstudiante(Long id) {
        if(puedoEliminarEstudiante(id)){
            PersonaEntity estudianteEntity = this.repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("El estudiante con id " + id + " no existe."));
            estudianteEntity.setEstado(Boolean.FALSE);
            this.repository.save(estudianteEntity);
        } else {
            throw new RuntimeException("No se puede eliminar porque no es estudiante o actualmente se encuentra matriculado.");
        }
    }

    private Boolean existByIdentificacion(String identificacion) {
        return this.repository.existsByIdentificacion(identificacion);
    }

    private void setEdad(PersonaEntity estudianteEntity) {
        Integer edad = (new Date().getYear() - estudianteEntity.getFechaNacimiento().getYear());
        estudianteEntity.setEdad(edad);
    }

    private Boolean existeEstudiante(Long estId) {
        return repository.findByIdPersonaAndTipoPersonaCodigo(estId, CODIGO_ESTUDIANTE).isPresent();
    }

    private Boolean estudianteNoMatriculado(Long estId){
        return matriculaService.obtenerPorEstudianteId(estId).isEmpty();
    }

    private Boolean puedoEliminarEstudiante(Long estId){
        return existeEstudiante(estId) && estudianteNoMatriculado(estId);
    }
}

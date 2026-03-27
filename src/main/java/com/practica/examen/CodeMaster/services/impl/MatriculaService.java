package com.practica.examen.CodeMaster.services.impl;

import com.practica.examen.CodeMaster.entities.MatriculaEntity;
import com.practica.examen.CodeMaster.repositories.IMatriculaRepository;
import com.practica.examen.CodeMaster.services.IMatriculaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatriculaService implements IMatriculaService {
    private final IMatriculaRepository repository;
    private final Integer CUPO_MAXIMO = 5;

    @Override
    public void guardar(MatriculaEntity entity) {
        if(verificarNumeroMatriculadosPorGrupo(entity.getGrupo().getIdGrupo())){
            throw new RuntimeException("El grupo seleccionado ya se encuentra lleno.");
        }
        this.repository.save(entity);
    }

    @Override
    public List<MatriculaEntity> listar() {
        return this.repository.findAll();
    }

    @Override
    public List<MatriculaEntity> obtenerPorEstudianteId(Long idPersona) {
        return this.repository.findByEstadoTrueAndPersona_IdPersona(idPersona);
    }

    private Boolean verificarNumeroMatriculadosPorGrupo(Long grupoId){
        return this.repository.countByGrupoIdGrupoAndEstadoTrue(grupoId) >= CUPO_MAXIMO;
    }
}
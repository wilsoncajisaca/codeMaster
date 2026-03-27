package com.practica.examen.CodeMaster.services.impl;

import com.practica.examen.CodeMaster.entities.TipoPersonaEntity;
import com.practica.examen.CodeMaster.repositories.ITipoPersonaRepository;
import com.practica.examen.CodeMaster.services.ITipoPersonaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoPersonaService implements ITipoPersonaService {
    private final ITipoPersonaRepository repository;

    @Override
    public void guardar(TipoPersonaEntity entity) {
        this.repository.save(entity);
    }

    @Override
    public List<TipoPersonaEntity> listar() {
        return repository.findByEstadoTrue();
    }
}

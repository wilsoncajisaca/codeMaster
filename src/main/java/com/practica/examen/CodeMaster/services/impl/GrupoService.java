package com.practica.examen.CodeMaster.services.impl;

import com.practica.examen.CodeMaster.entities.GrupoEntity;
import com.practica.examen.CodeMaster.repositories.IGrupoRepository;
import com.practica.examen.CodeMaster.services.IGrupoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GrupoService implements IGrupoService {
    private final IGrupoRepository repository;
    @Override
    public void guardar(GrupoEntity entity) {
        repository.save(entity);
    }

    @Override
    public List<GrupoEntity> listar() {
        return repository.findAll();
    }
}

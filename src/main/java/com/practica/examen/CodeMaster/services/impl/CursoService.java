package com.practica.examen.CodeMaster.services.impl;

import com.practica.examen.CodeMaster.entities.CursoEntity;
import com.practica.examen.CodeMaster.repositories.ICursoRepository;
import com.practica.examen.CodeMaster.services.ICursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CursoService implements ICursoService {
    private final ICursoRepository repository;

    @Override
    public void guardar(CursoEntity entity) {
        this.repository.save(entity);
    }

    @Override
    public void actualizar(CursoEntity entity) {
        this.repository.save(entity);
    }

    @Override
    public List<CursoEntity> listar() {
        return this.repository.findAll();
    }
}

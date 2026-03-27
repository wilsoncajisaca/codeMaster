package com.practica.examen.CodeMaster.services.impl;

import com.practica.examen.CodeMaster.entities.EvaluacionEntity;
import com.practica.examen.CodeMaster.repositories.IEvaluacionRepository;
import com.practica.examen.CodeMaster.services.IEvaluacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluacionService implements IEvaluacionService{
    private final IEvaluacionRepository repository;

    @Override
    public void guardar(EvaluacionEntity entity) {
        this.repository.save(entity);
    }

    @Override
    public List<EvaluacionEntity> listar() {
        return this.repository.findAll();
    }
}

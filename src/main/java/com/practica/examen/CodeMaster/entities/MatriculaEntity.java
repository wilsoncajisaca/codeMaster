package com.practica.examen.CodeMaster.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "matricula")
public class MatriculaEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long idMatricula;

    private String fechaMatricula;
    private Boolean estado = Boolean.TRUE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEstudiante")
    private PersonaEntity persona;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idGrupo")
    private GrupoEntity grupo;
}
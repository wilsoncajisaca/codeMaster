package com.practica.examen.CodeMaster.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "curso")
public class CursoEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long idCurso;
    private String codigoCurso;
    private String nombre;
    private String descripcion;
    private Integer duracionHoras;
    private String nivel;
    private Boolean estado= Boolean.TRUE;
}
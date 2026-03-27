package com.practica.examen.CodeMaster.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "grupo")
public class GrupoEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long idGrupo;

    @Column(unique = true)
    private String codigoGrupo;

    private String horario;
    private Integer cupoMaximo;
    private Date fechaInicio;
    private Date fechaFin;
    private Boolean estado = Boolean.TRUE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCurso")
    private CursoEntity curso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPersona")
    private PersonaEntity docente;
}

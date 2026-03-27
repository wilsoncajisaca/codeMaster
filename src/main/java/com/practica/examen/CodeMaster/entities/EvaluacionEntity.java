package com.practica.examen.CodeMaster.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "evaluacion")
public class EvaluacionEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long idEvaluacion;

    private Integer deberes;
    private Integer examen;

    private Boolean estado = Boolean.TRUE;

    @OneToOne
    @JoinColumn(name = "id_matricula", nullable = false, unique = true)
    private MatriculaEntity matricula;
}

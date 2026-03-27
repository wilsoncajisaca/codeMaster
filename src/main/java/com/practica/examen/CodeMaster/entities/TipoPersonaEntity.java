package com.practica.examen.CodeMaster.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "tipo_persona")
public class TipoPersonaEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoPersona;
    @Column(unique = true, nullable = false)
    private String codigo;
    private String nombre;
    private Boolean estado = Boolean.TRUE;
}

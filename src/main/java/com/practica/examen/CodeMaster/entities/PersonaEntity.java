package com.practica.examen.CodeMaster.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "persona")
public class PersonaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long idPersona;

    @Column(nullable = false)
    private String tipoIdentificacion;

    @Column(nullable = false,unique = true)
    private String identificacion;

    @Column(nullable = false)
    private String nombres;

    @Column(nullable = false)
    private String apellidos;
    private Integer edad;
    private String sexo;

    private Boolean estado=Boolean.TRUE;

    @Transient
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaNacimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idTipoPersona")
    private TipoPersonaEntity tipoPersona;
}

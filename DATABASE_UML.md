# Modelo de Base de Datos - Sistema CodeMaster
## Diagrama Entidad-Relación Optimizado (3 horas implementación)

```
┌─────────────────────────────────────┐
│          ESTUDIANTE                 │
├─────────────────────────────────────┤
│ PK  id                  BIGINT      │
│     tipo_identificacion VARCHAR(20) │
│ UK  numero_identificacion VARCHAR(20)│
│     nombres             VARCHAR(100)│
│     apellidos           VARCHAR(100)│
│     fecha_nacimiento    DATE        │
│     sexo                CHAR(1)     │
│     edad                INTEGER     │◄────── Calculado
│     activo              BOOLEAN     │
│     fecha_creacion      TIMESTAMP   │
└─────────────────────────────────────┘
            │
            │ 1
            │
            │ *
┌─────────────────────────────────────┐
│         INSCRIPCION                 │
├─────────────────────────────────────┤
│ PK  id                  BIGINT      │
│ FK  estudiante_id       BIGINT      │
│ FK  curso_id            BIGINT      │
│     fecha_inscripcion   DATE        │
│     activo              BOOLEAN     │
└─────────────────────────────────────┘
            │
            │ 1
            │
            │ 1
┌─────────────────────────────────────┐
│        CALIFICACION                 │
├─────────────────────────────────────┤
│ PK  id                  BIGINT      │
│ FK  inscripcion_id      BIGINT      │
│     deberes             DECIMAL(4,2)│
│     examen              DECIMAL(4,2)│
│     promedio            DECIMAL(4,2)│◄────── Calculado
│     fecha_actualizacion TIMESTAMP   │
└─────────────────────────────────────┘


┌─────────────────────────────────────┐
│           PROFESOR                  │
├─────────────────────────────────────┤
│ PK  id                  BIGINT      │
│     tipo_identificacion VARCHAR(20) │
│ UK  numero_identificacion VARCHAR(20)│
│     nombres             VARCHAR(100)│
│     apellidos           VARCHAR(100)│
│     fecha_nacimiento    DATE        │
│     sexo                CHAR(1)     │
│     edad                INTEGER     │◄────── Calculado
│     activo              BOOLEAN     │
│     fecha_creacion      TIMESTAMP   │
└─────────────────────────────────────┘
            │
            │ 1
            │
            │ *
┌─────────────────────────────────────┐
│             CURSO                   │
├─────────────────────────────────────┤
│ PK  id                  BIGINT      │
│ UK  codigo              VARCHAR(20) │
│     nombre              VARCHAR(100)│
│     descripcion         TEXT        │
│     duracion_horas      INTEGER     │
│ FK  profesor_id         BIGINT      │
│     activo              BOOLEAN     │
│     fecha_creacion      TIMESTAMP   │
└─────────────────────────────────────┘
            │
            │ 1
            │
            │ * (máx 5)
            └──────────────┐
                           │
                           ▼
                     INSCRIPCION
```

---

## Relaciones

| Relación | Cardinalidad | Descripción | Constraint |
|---|---|---|---|
| ESTUDIANTE → INSCRIPCION | 1:N | Un estudiante puede inscribirse en varios cursos | - |
| CURSO → INSCRIPCION | 1:N | Un curso puede tener varias inscripciones | **MAX 5** ✓ |
| PROFESOR → CURSO | 1:N | Un profesor puede tener varios cursos | - |
| INSCRIPCION → CALIFICACION | 1:1 | Una inscripción tiene una calificación | - |

---

## Script SQL de Creación (PostgreSQL)

```sql
-- Tabla ESTUDIANTE
CREATE TABLE estudiante (
    id BIGSERIAL PRIMARY KEY,
    tipo_identificacion VARCHAR(20) NOT NULL,
    numero_identificacion VARCHAR(20) NOT NULL UNIQUE,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    sexo CHAR(1) NOT NULL CHECK (sexo IN ('M', 'F')),
    edad INTEGER NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_edad_estudiante CHECK (edad >= 15 AND edad <= 100)
);

-- Tabla PROFESOR
CREATE TABLE profesor (
    id BIGSERIAL PRIMARY KEY,
    tipo_identificacion VARCHAR(20) NOT NULL,
    numero_identificacion VARCHAR(20) NOT NULL UNIQUE,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    sexo CHAR(1) NOT NULL CHECK (sexo IN ('M', 'F')),
    edad INTEGER NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_edad_profesor CHECK (edad >= 18 AND edad <= 100)
);

-- Tabla CURSO
CREATE TABLE curso (
    id BIGSERIAL PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    duracion_horas INTEGER,
    profesor_id BIGINT,
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_curso_profesor FOREIGN KEY (profesor_id) 
        REFERENCES profesor(id) ON DELETE SET NULL
);

-- Tabla INSCRIPCION
CREATE TABLE inscripcion (
    id BIGSERIAL PRIMARY KEY,
    estudiante_id BIGINT NOT NULL,
    curso_id BIGINT NOT NULL,
    fecha_inscripcion DATE DEFAULT CURRENT_DATE,
    activo BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_inscripcion_estudiante FOREIGN KEY (estudiante_id) 
        REFERENCES estudiante(id) ON DELETE CASCADE,
    CONSTRAINT fk_inscripcion_curso FOREIGN KEY (curso_id) 
        REFERENCES curso(id) ON DELETE CASCADE,
    CONSTRAINT uk_estudiante_curso UNIQUE (estudiante_id, curso_id)
);

-- Tabla CALIFICACION
CREATE TABLE calificacion (
    id BIGSERIAL PRIMARY KEY,
    inscripcion_id BIGINT NOT NULL UNIQUE,
    deberes DECIMAL(4,2) CHECK (deberes >= 0 AND deberes <= 10),
    examen DECIMAL(4,2) CHECK (examen >= 0 AND examen <= 10),
    promedio DECIMAL(4,2) CHECK (promedio >= 0 AND promedio <= 10),
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_calificacion_inscripcion FOREIGN KEY (inscripcion_id) 
        REFERENCES inscripcion(id) ON DELETE CASCADE
);

-- Índices para optimizar consultas
CREATE INDEX idx_estudiante_numero_id ON estudiante(numero_identificacion);
CREATE INDEX idx_profesor_numero_id ON profesor(numero_identificacion);
CREATE INDEX idx_curso_codigo ON curso(codigo);
CREATE INDEX idx_inscripcion_estudiante ON inscripcion(estudiante_id);
CREATE INDEX idx_inscripcion_curso ON inscripcion(curso_id);
CREATE INDEX idx_calificacion_inscripcion ON calificacion(inscripcion_id);

-- Función para validar máximo 5 estudiantes por curso
CREATE OR REPLACE FUNCTION validar_capacidad_curso()
RETURNS TRIGGER AS $$
BEGIN
    IF (SELECT COUNT(*) FROM inscripcion 
        WHERE curso_id = NEW.curso_id AND activo = TRUE) >= 5 THEN
        RAISE EXCEPTION 'El curso ha alcanzado la capacidad máxima de 5 estudiantes';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger para validar capacidad antes de insertar
CREATE TRIGGER trg_validar_capacidad
    BEFORE INSERT ON inscripcion
    FOR EACH ROW
    EXECUTE FUNCTION validar_capacidad_curso();

-- Función para calcular promedio automáticamente
CREATE OR REPLACE FUNCTION calcular_promedio()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.deberes IS NOT NULL AND NEW.examen IS NOT NULL THEN
        NEW.promedio := ROUND((NEW.deberes + NEW.examen) / 2, 2);
    ELSE
        NEW.promedio := NULL;
    END IF;
    NEW.fecha_actualizacion := CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger para calcular promedio en INSERT y UPDATE
CREATE TRIGGER trg_calcular_promedio
    BEFORE INSERT OR UPDATE OF deberes, examen ON calificacion
    FOR EACH ROW
    EXECUTE FUNCTION calcular_promedio();
```

---

## Reglas de Integridad Implementadas

| Regla | Implementación | Nivel |
|---|---|---|
| **RN-001: Campos obligatorios** | `NOT NULL` en todos los campos requeridos | Base de Datos |
| **RN-002: Edad automática** | Calculada en lógica de negocio (Service) | Aplicación |
| **RN-003: ID único** | `UNIQUE CONSTRAINT` en numero_identificacion | Base de Datos |
| **RN-004: Máx 5 estudiantes** | `TRIGGER validar_capacidad_curso()` | Base de Datos |
| **RN-005: No eliminar con cursos** | Validación en Service antes de eliminar | Aplicación |
| **RN-006: Promedio automático** | `TRIGGER calcular_promedio()` | Base de Datos |
| **RN-007: Calificaciones 0-10** | `CHECK CONSTRAINT` en deberes y examen | Base de Datos |
| **RN-008: Aprobado ≥7.0** | Lógica calculada en consultas/reportes | Aplicación |

---

## Modelo de Entidades JPA Simplificado

### Entidad Base (Persona)
```java
@MappedSuperclass
public abstract class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Column(name = "tipo_identificacion", nullable = false)
    private String tipoIdentificacion; // "CÉDULA", "PASAPORTE", "RUC"
    
    @NotBlank
    @Column(name = "numero_identificacion", unique = true, nullable = false)
    private String numeroIdentificacion;
    
    @NotBlank
    @Column(nullable = false)
    private String nombres;
    
    @NotBlank
    @Column(nullable = false)
    private String apellidos;
    
    @NotNull
    @Past
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;
    
    @NotNull
    @Column(nullable = false, length = 1)
    private Character sexo; // 'M' o 'F'
    
    @Column(nullable = false)
    private Integer edad; // Calculado antes de guardar
    
    private Boolean activo = true;
    
    @CreationTimestamp
    private LocalDateTime fechaCreacion;
    
    // Método para calcular edad
    @PrePersist
    @PreUpdate
    public void calcularEdad() {
        if (fechaNacimiento != null) {
            this.edad = Period.between(fechaNacimiento, LocalDate.now()).getYears();
        }
    }
}
```

### 1. Estudiante
```java
@Entity
@Table(name = "estudiante")
public class Estudiante extends Persona {
    
    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL)
    private List<Inscripcion> inscripciones = new ArrayList<>();
    
    // Constructor, getters, setters
}
```

### 2. Profesor
```java
@Entity
@Table(name = "profesor")
public class Profesor extends Persona {
    
    @OneToMany(mappedBy = "profesor")
    private List<Curso> cursos = new ArrayList<>();
    
    // Constructor, getters, setters
}
```

### 3. Curso
```java
@Entity
@Table(name = "curso")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Column(unique = true, nullable = false)
    private String codigo;
    
    @NotBlank
    @Column(nullable = false)
    private String nombre;
    
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(name = "duracion_horas")
    private Integer duracionHoras;
    
    @ManyToOne
    @JoinColumn(name = "profesor_id")
    private Profesor profesor;
    
    @OneToMany(mappedBy = "curso")
    private List<Inscripcion> inscripciones = new ArrayList<>();
    
    private Boolean activo = true;
    
    @CreationTimestamp
    private LocalDateTime fechaCreacion;
    
    // Método para validar capacidad
    public boolean puedeInscribirEstudiante() {
        long activos = inscripciones.stream()
            .filter(Inscripcion::getActivo)
            .count();
        return activos < 5;
    }
    
    // Constructor, getters, setters
}
```

### 4. Inscripcion
```java
@Entity
@Table(name = "inscripcion", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"estudiante_id", "curso_id"}))
public class Inscripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "estudiante_id", nullable = false)
    private Estudiante estudiante;
    
    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;
    
    @Column(name = "fecha_inscripcion")
    private LocalDate fechaInscripcion = LocalDate.now();
    
    @OneToOne(mappedBy = "inscripcion", cascade = CascadeType.ALL)
    private Calificacion calificacion;
    
    private Boolean activo = true;
    
    // Constructor, getters, setters
}
```

### 5. Calificacion
```java
@Entity
@Table(name = "calificacion")
public class Calificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "inscripcion_id", unique = true, nullable = false)
    private Inscripcion inscripcion;
    
    @DecimalMin("0.0")
    @DecimalMax("10.0")
    @Column(precision = 4, scale = 2)
    private BigDecimal deberes;
    
    @DecimalMin("0.0")
    @DecimalMax("10.0")
    @Column(precision = 4, scale = 2)
    private BigDecimal examen;
    
    @Column(precision = 4, scale = 2)
    private BigDecimal promedio; // Calculado automáticamente
    
    @UpdateTimestamp
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    // Calcular promedio antes de persistir
    @PrePersist
    @PreUpdate
    public void calcularPromedio() {
        if (deberes != null && examen != null) {
            this.promedio = deberes.add(examen)
                .divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP);
        } else {
            this.promedio = null;
        }
    }
    
    // Método para verificar si aprobó
    public boolean aprobado() {
        return promedio != null && promedio.compareTo(BigDecimal.valueOf(7.0)) >= 0;
    }
    
    // Constructor, getters, setters
}
```

---

## Resumen de Tablas

| Tabla | Registros Estimados | Índices | Triggers | Descripción |
|---|---|---|---|---|
| **estudiante** | ~100-500 | 2 | - | Información personal de estudiantes |
| **profesor** | ~10-50 | 2 | - | Información personal de profesores |
| **curso** | ~20-100 | 2 | - | Catálogo de cursos disponibles |
| **inscripcion** | ~500-2000 | 3 | validar_capacidad | Matrícula estudiante-curso |
| **calificacion** | ~500-2000 | 2 | calcular_promedio | Notas y promedio de estudiantes |

---

## Consultas SQL Frecuentes

### 1. Listar estudiantes con sus cursos
```sql
SELECT e.nombres, e.apellidos, c.nombre AS curso, c.codigo
FROM estudiante e
JOIN inscripcion i ON e.id = i.estudiante_id
JOIN curso c ON i.curso_id = c.id
WHERE e.activo = TRUE AND i.activo = TRUE;
```

### 2. Ver capacidad de cursos
```sql
SELECT 
    c.codigo,
    c.nombre,
    p.nombres || ' ' || p.apellidos AS profesor,
    COUNT(i.id) AS inscritos,
    5 - COUNT(i.id) AS cupos_disponibles
FROM curso c
LEFT JOIN profesor p ON c.profesor_id = p.id
LEFT JOIN inscripcion i ON c.id = i.curso_id AND i.activo = TRUE
GROUP BY c.id, c.codigo, c.nombre, p.nombres, p.apellidos
ORDER BY inscritos DESC;
```

### 3. Reporte de calificaciones por curso
```sql
SELECT 
    e.numero_identificacion,
    e.nombres || ' ' || e.apellidos AS estudiante,
    cal.deberes,
    cal.examen,
    cal.promedio,
    CASE 
        WHEN cal.promedio >= 7.0 THEN 'APROBADO'
        WHEN cal.promedio < 7.0 THEN 'REPROBADO'
        ELSE 'PENDIENTE'
    END AS estado
FROM curso c
JOIN inscripcion i ON c.id = i.curso_id
JOIN estudiante e ON i.estudiante_id = e.id
LEFT JOIN calificacion cal ON i.id = cal.inscripcion_id
WHERE c.id = ? AND i.activo = TRUE
ORDER BY e.apellidos, e.nombres;
```

### 4. Verificar si estudiante puede ser eliminado
```sql
SELECT COUNT(*) AS cursos_activos
FROM inscripcion
WHERE estudiante_id = ? AND activo = TRUE;
-- Si retorna 0, se puede eliminar
```

### 5. Validar duplicidad de identificación
```sql
SELECT 
    'ESTUDIANTE' AS tipo,
    id,
    nombres || ' ' || apellidos AS nombre_completo
FROM estudiante
WHERE numero_identificacion = ?
UNION ALL
SELECT 
    'PROFESOR' AS tipo,
    id,
    nombres || ' ' || apellidos AS nombre_completo
FROM profesor
WHERE numero_identificacion = ?;
-- Si retorna resultados, el ID ya existe
```

---

## Datos de Prueba (Seeds)

```sql
-- PROFESORES
INSERT INTO profesor (tipo_identificacion, numero_identificacion, nombres, apellidos, fecha_nacimiento, sexo, edad) VALUES
('CÉDULA', '1234567890', 'Carlos', 'Mendoza', '1985-05-15', 'M', 40),
('CÉDULA', '0987654321', 'María', 'González', '1990-08-22', 'F', 35),
('PASAPORTE', 'ABC123456', 'Juan', 'Pérez', '1982-12-10', 'M', 43);

-- CURSOS
INSERT INTO curso (codigo, nombre, descripcion, duracion_horas, profesor_id) VALUES
('JAVA-101', 'Java Básico', 'Fundamentos de programación en Java', 40, 1),
('PY-201', 'Python Avanzado', 'Python para ciencia de datos', 60, 2),
('WEB-301', 'Desarrollo Web Full Stack', 'React + Node.js', 80, 1);

-- ESTUDIANTES
INSERT INTO estudiante (tipo_identificacion, numero_identificacion, nombres, apellidos, fecha_nacimiento, sexo, edad) VALUES
('CÉDULA', '1122334455', 'Ana', 'López', '2005-03-20', 'F', 21),
('CÉDULA', '2233445566', 'Pedro', 'Ramírez', '2004-07-15', 'M', 21),
('CÉDULA', '3344556677', 'Laura', 'Torres', '2006-11-30', 'F', 19),
('CÉDULA', '4455667788', 'Diego', 'Silva', '2005-09-08', 'M', 20);

-- INSCRIPCIONES
INSERT INTO inscripcion (estudiante_id, curso_id, fecha_inscripcion) VALUES
(1, 1, '2026-01-15'),
(2, 1, '2026-01-16'),
(3, 1, '2026-01-17'),
(1, 2, '2026-02-01'),
(4, 3, '2026-02-10');

-- CALIFICACIONES
INSERT INTO calificacion (inscripcion_id, deberes, examen) VALUES
(1, 8.50, 9.00),  -- Promedio: 8.75 APROBADO
(2, 6.00, 7.50),  -- Promedio: 6.75 REPROBADO
(3, 9.00, 8.50),  -- Promedio: 8.75 APROBADO
(4, 7.00, NULL);  -- Promedio: NULL PENDIENTE
```

---

## Consideraciones para Implementación en 3 Horas

### ✅ Prioridades (Orden de implementación)

**Hora 1: Tablas Base**
1. Crear tabla `estudiante` con constraints
2. Crear tabla `profesor` con constraints
3. Insertar datos de prueba (3 profesores, 5 estudiantes)

**Hora 2: Relaciones**
4. Crear tabla `curso` con FK a profesor
5. Crear tabla `inscripcion` con FKs
6. Crear trigger de validación de capacidad (máx 5)
7. Insertar cursos e inscripciones de prueba

**Hora 3: Calificaciones**
8. Crear tabla `calificacion` con validaciones
9. Crear trigger de cálculo automático de promedio
10. Insertar calificaciones de prueba
11. Probar consultas principales

### ⚡ Optimizaciones para ahorro de tiempo:

- **NO** implementar autenticación/usuarios (fuera de MVP de 3 horas)
- **NO** crear vistas materializadas (usar consultas simples)
- **NO** implementar auditoría completa (solo timestamps básicos)
- **SÍ** usar triggers para reglas automáticas (edad, promedio, capacidad)
- **SÍ** usar constraints de BD para validaciones (más rápido que en código)

---

## Configuración application.yaml

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/codemaster_db
    username: postgres
    password: admin
    driver-class-name: org.postgresql.Driver
  
  jpa:
    hibernate:
      ddl-auto: create-drop  # Cambiar a 'update' en producción
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.PostgreSQLDialect
    
  application:
    name: CodeMaster

server:
  port: 8080
```

---

## Checklist de Implementación

- [ ] Crear base de datos `codemaster_db` en PostgreSQL
- [ ] Ejecutar script de creación de tablas
- [ ] Ejecutar script de triggers y funciones
- [ ] Crear índices
- [ ] Insertar datos de prueba
- [ ] Probar restricción de 5 estudiantes por curso
- [ ] Probar cálculo automático de promedio
- [ ] Probar validación de eliminación con cursos activos
- [ ] Verificar unicidad de identificaciones

---

**Tiempo estimado de implementación: 3 horas**  
**Última actualización:** 2026-03-25


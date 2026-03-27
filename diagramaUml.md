erDiagram
    ESTUDIANTE {
        int id_estudiante PK
        string tipo_identificacion
        string numero_identificacion UK
        string nombres
        string apellidos
        date fecha_nacimiento
        int edad
        string sexo
    }

    DOCENTE {
        int id_docente PK
        string tipo_identificacion
        string numero_identificacion UK
        string nombres
        string apellidos
        date fecha_nacimiento
        int edad
        string sexo
    }

    CURSO {
        int id_curso PK
        string nombre
        string descripcion
        int cupo_maximo
        int id_docente FK
    }

    MATRICULA {
        int id_matricula PK
        int id_estudiante FK
        int id_curso FK
        date fecha_matricula
        string estado
    }

    CALIFICACION {
        int id_calificacion PK
        int id_matricula FK
        decimal deberes
        decimal examen
        decimal promedio
    }

    DOCENTE ||--o{ CURSO : imparte
    ESTUDIANTE ||--o{ MATRICULA : realiza
    CURSO ||--o{ MATRICULA : contiene
    MATRICULA ||--|| CALIFICACION : genera
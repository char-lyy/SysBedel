CREATE SCHEMA sysbedel;
USE sysbedel;

CREATE TABLE Departamento (
    codigoDepartamento VARCHAR(50) PRIMARY KEY NOT NULL,
    nombreDepartamento VARCHAR(100) NOT NULL
);

CREATE TABLE Docente (
    legajoDocente VARCHAR(50) PRIMARY KEY NOT NULL,
    nombreDocente VARCHAR(100) NOT NULL,
    apellidoDocente VARCHAR(100) NOT NULL,
    mailDocente VARCHAR(100) NOT NULL,
    estadoDocente BOOLEAN NOT NULL,
    tituloDocente VARCHAR(100) NOT NULL,
    telefonoDocente VARCHAR(50) NOT NULL
);

CREATE TABLE CatedraDocente (
    legajoDocente VARCHAR(50) NOT NULL,
    codigoCatedra VARCHAR(50) NOT NULL,
    PRIMARY KEY (legajoDocente, codigoCatedra),
    FOREIGN KEY (legajoDocente) REFERENCES Docente(legajoDocente),
    FOREIGN KEY (codigoCatedra) REFERENCES Departamento(codigoDepartamento)
);

CREATE TABLE Aula (
    numeroAula INT PRIMARY KEY NOT NULL,
    capacidadAula INT NOT NULL,
    llaveAsignada VARCHAR(50) NOT NULL,
    BooleanAula BOOLEAN NOT NULL
);

CREATE TABLE Horario (
    codigoHorario VARCHAR(50) PRIMARY KEY NOT NULL,
    dia VARCHAR(50) NOT NULL,
    horaInicio TIME NOT NULL,
    horaFin TIME NOT NULL
);

CREATE TABLE Actividad (
    codigoActividad VARCHAR(50) PRIMARY KEY NOT NULL,
    numeroAula INT NOT NULL,
    codigoHorario VARCHAR(50) NOT NULL,
    fechaInicioActividad DATETIME NOT NULL,
    fechaFinActividad DATETIME NOT NULL,
    periodoActividad VARCHAR(50) NOT NULL,
    tipoActividad VARCHAR(50) NOT NULL,
    FOREIGN KEY (numeroAula) REFERENCES Aula(numeroAula),
    FOREIGN KEY (codigoHorario) REFERENCES Horario(codigoHorario)
);

CREATE TABLE Clase (
    codigoActividad VARCHAR(50) NOT NULL,
    legajoDocente VARCHAR(50) NOT NULL,
    asignatura VARCHAR(100) NOT NULL,
    PRIMARY KEY (codigoActividad, legajoDocente),
    FOREIGN KEY (codigoActividad) REFERENCES Actividad(codigoActividad),
    FOREIGN KEY (legajoDocente) REFERENCES Docente(legajoDocente)
);

CREATE TABLE Evento (
    codigoActividad VARCHAR(50) NOT NULL,
    responsableEvento VARCHAR(100) NOT NULL,
    descripcionEvento VARCHAR(255) NOT NULL,
    nroNotaEvento VARCHAR(50) NOT NULL,
    PRIMARY KEY (codigoActividad),
    FOREIGN KEY (codigoActividad) REFERENCES Actividad(codigoActividad)
);

CREATE TABLE Recurso (
    codigoRecurso VARCHAR(50) PRIMARY KEY NOT NULL,
    descripcionRecurso VARCHAR(255) NOT NULL,
    cantidadRecurso INT NOT NULL
);

CREATE TABLE Prestamo (
    legajoDocente VARCHAR(50) NOT NULL,
    codigoRecurso VARCHAR(50) NOT NULL,
    estaPrestado BOOLEAN NOT NULL,
    fechaPrestamo DATETIME NOT NULL,
    horaPrestamo DATETIME NOT NULL,
    observacionPrestamo VARCHAR(255),
    PRIMARY KEY (legajoDocente, codigoRecurso),
    FOREIGN KEY (legajoDocente) REFERENCES Docente(legajoDocente),
    FOREIGN KEY (codigoRecurso) REFERENCES Recurso(codigoRecurso)
);

CREATE TABLE Observacion (
    codigoObservacion INT PRIMARY KEY NOT NULL,
    numeroAula INT NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    fechaObservacion DATETIME NOT NULL,
    FOREIGN KEY (numeroAula) REFERENCES Aula(numeroAula)
);

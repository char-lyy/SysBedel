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
    ocupada BOOLEAN NOT NULL
);

CREATE TABLE Actividad (
    idActividad INT PRIMARY KEY,
    fechaInicioActividad DATE NOT NULL,
    fechaFinActividad DATE NOT NULL,
    periodoActividad VARCHAR(50) NOT NULL,
    tipoActividad VARCHAR(50) NOT NULL
);

CREATE TABLE Reserva (
    idReserva INT PRIMARY KEY,
    idActividad INT NOT NULL,
    numeroAula INT NOT NULL,
    confirmada BOOLEAN NOT NULL DEFAULT FALSE,
    horaInicio TIME NOT NULL,
    horaFin TIME NOT NULL,
    fechaReserva DATE NOT NULL, -- Fecha en el que se realizo la reserva
    fechaActividad DATE NOT NULL, -- Fecha en la que se realiza la actividad
    FOREIGN KEY (idActividad) REFERENCES Actividad(idActividad),
    FOREIGN KEY (numeroAula) REFERENCES Aula(numeroAula)
);
CREATE TABLE Clase (
    idActividad INT PRIMARY KEY,
    legajoDocente VARCHAR(50) NOT NULL,
    asignatura VARCHAR(100) NOT NULL,
    PRIMARY KEY (idActividad, legajoDocente),
    FOREIGN KEY (idActividad) REFERENCES Actividad(idActividad),
    FOREIGN KEY (legajoDocente) REFERENCES Docente(legajoDocente)
);

CREATE TABLE Evento (
    idActividad INT PRIMARY KEY,
    responsableEvento VARCHAR(100) NOT NULL,
    descripcionEvento VARCHAR(255) NOT NULL,
    nroNotaEvento VARCHAR(50) NOT NULL,
    PRIMARY KEY (idActividad),
    FOREIGN KEY (idActividad) REFERENCES Actividad(idActividad)
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
    fechaPrestamo DATE NOT NULL,
    horaPrestamo DATE NOT NULL,
    observacionPrestamo VARCHAR(255),
    PRIMARY KEY (legajoDocente, codigoRecurso),
    FOREIGN KEY (legajoDocente) REFERENCES Docente(legajoDocente),
    FOREIGN KEY (codigoRecurso) REFERENCES Recurso(codigoRecurso)
);

CREATE TABLE Observacion (
    codigoObservacion INT PRIMARY KEY NOT NULL,
    numeroAula INT NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    fechaObservacion DATE NOT NULL,
    horaObservacion TIME NOT NULL,
    FOREIGN KEY (numeroAula) REFERENCES Aula(numeroAula)
);

INSERT INTO Aula (numeroAula, capacidadAula, ocupada) VALUES (20, 30, FALSE);
INSERT INTO Aula (numeroAula, capacidadAula, ocupada) VALUES (21, 30, FALSE);
INSERT INTO Aula (numeroAula, capacidadAula, ocupada) VALUES (22, 30, FALSE);
INSERT INTO Aula (numeroAula, capacidadAula, ocupada) VALUES (23, 30, FALSE);
INSERT INTO Aula (numeroAula, capacidadAula, ocupada) VALUES (24, 30, FALSE);
INSERT INTO Aula (numeroAula, capacidadAula, ocupada) VALUES (25, 30, FALSE);
INSERT INTO Aula (numeroAula, capacidadAula, ocupada) VALUES (26, 30, FALSE);
INSERT INTO Aula (numeroAula, capacidadAula, ocupada) VALUES (27, 30, FALSE);
INSERT INTO Aula (numeroAula, capacidadAula, ocupada) VALUES (28, 30, FALSE);
INSERT INTO Aula (numeroAula, capacidadAula, ocupada) VALUES (29, 30, FALSE);
INSERT INTO Aula (numeroAula, capacidadAula, ocupada) VALUES (30, 30, FALSE);

INSERT INTO Reserva(idReserva, idActividad, numeroAula, confirmada, horaInicio, horaFin, fechaReserva, fechaActividad) VALUES (1, 1, 24, 1, '8:00:00', '10:00:00', 2024-09-23, 2024-09-23);
INSERT INTO Reserva(idReserva, idActividad, numeroAula, confirmada, horaInicio, horaFin, fechaReserva, fechaActividad) VALUES (2, 1, 24, 1, '8:00:00', '10:00:00', 2024-09-23, 2024-09-23);

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
    idActividad INT,
    legajoDocente VARCHAR(50) NOT NULL,
    asignatura VARCHAR(100) NOT NULL,
    PRIMARY KEY (idActividad, legajoDocente),
    FOREIGN KEY (idActividad) REFERENCES Actividad(idActividad),
    FOREIGN KEY (legajoDocente) REFERENCES Docente(legajoDocente)
);


CREATE TABLE Evento (
    idActividad INT,
    responsableEvento VARCHAR(100) NOT NULL,
    descripcionEvento VARCHAR(255) NOT NULL,
    nroNotaEvento VARCHAR(50) NOT NULL,
    PRIMARY KEY (idActividad),
    FOREIGN KEY (idActividad) REFERENCES Actividad(idActividad)
);

CREATE TABLE Recurso (
    codigoRecurso INT AUTO_INCREMENT PRIMARY KEY,
    descripcionRecurso VARCHAR(255) NOT NULL,
    cantidadRecurso INT NOT NULL
);

CREATE TABLE informe (
    numero_aula INT NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    PRIMARY KEY (numero_aula, descripcion)
);

CREATE TABLE Prestamo (
    legajoDocente VARCHAR(50) NOT NULL,
    codigoRecurso INT NOT NULL,
    estaPrestado BOOLEAN NOT NULL,
    fechaPrestamo DATE NOT NULL,
    horaPrestamo TIME NOT NULL,
    observacionPrestamo VARCHAR(255),
    PRIMARY KEY (legajoDocente, codigoRecurso),
    FOREIGN KEY (legajoDocente) REFERENCES Docente(legajoDocente),
    FOREIGN KEY (codigoRecurso) REFERENCES Recurso(codigoRecurso)  -- Sin AUTO_INCREMENT aquí
);

CREATE TABLE Observacion (
    codigoObservacion INT PRIMARY KEY NOT NULL,
    numeroAula INT NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    fechaObservacion DATE NOT NULL,
    horaObservacion TIME NOT NULL,
    FOREIGN KEY (numeroAula) REFERENCES Aula(numeroAula)
);

CREATE TABLE solicitudes (
    idSolicitud INT AUTO_INCREMENT PRIMARY KEY,
    codigoRecurso INT,
    descripcionRecurso VARCHAR(255),
    cantidadSolicitar INT,
    responsable VARCHAR(255),
    fecha DATE,
    hora TIME,
    FOREIGN KEY (codigoRecurso) REFERENCES recurso(codigoRecurso)
);



ALTER TABLE reserva ADD COLUMN descripcion VARCHAR(255);
ALTER TABLE reserva ADD COLUMN responsable VARCHAR(255);
ALTER TABLE actividad ADD COLUMN responsable VARCHAR(255);
ALTER TABLE Reserva MODIFY idReserva INT AUTO_INCREMENT;
ALTER TABLE aula ADD COLUMN observaciones VARCHAR(255);
ALTER TABLE aula ADD COLUMN responsable VARCHAR(255);
SET SQL_SAFE_UPDATES = 0;
ALTER TABLE aula ADD COLUMN fechaOcupacion DATE DEFAULT NULL;
UPDATE aula SET fechaOcupacion = '1970-01-01' WHERE fechaOcupacion IS NULL;
ALTER TABLE aula MODIFY COLUMN fechaOcupacion DATE NOT NULL;
SET SQL_SAFE_UPDATES = 1;  -- opcional
ALTER TABLE aula ADD COLUMN hora TIME NOT NULL;
ALTER TABLE aula DROP COLUMN fechaOcupacion;
ALTER TABLE aula ADD COLUMN fecha DATE DEFAULT NULL;
ALTER TABLE informe ADD COLUMN responsable VARCHAR(255);
ALTER TABLE informe ADD COLUMN hora TIME NOT NULL;
ALTER TABLE informe ADD COLUMN fecha DATE DEFAULT NULL;

INSERT INTO Aula (numeroAula, capacidadAula, ocupada, responsable, hora, fechaOcupacion) VALUES (31, 30, FALSE, 'Margarita', '19:00:00','2024-11-11');
INSERT INTO Actividad (idActividad, fechaInicioActividad, fechaFinActividad, periodoActividad, tipoActividad, responsable)
VALUES (2, '2024-09-23', '2024-09-23', 'Matutino', 'Clase','Margarita Alvarez');
INSERT INTO Reserva(idReserva, idActividad, numeroAula, confirmada, horaInicio, horaFin, fechaReserva, fechaActividad, responsable) 
VALUES (1, 1, 24, 1, '08:00:00', '10:00:00', '2024-09-23', '2024-09-23', 'Margarita Alvarez');
INSERT INTO Reserva(idReserva, idActividad, numeroAula, confirmada, horaInicio, horaFin, fechaReserva, fechaActividad, responsable) VALUES (2, 1, 24, 1, '8:00:00', '10:00:00', '2024-09-23', '2024-09-23', 'Mirtha Legrand');
INSERT INTO recurso (codigoRecurso,descripcionRecurso, cantidadRecurso) VALUES (1,'Proyector', 10);
INSERT INTO recurso (codigoRecurso,descripcionRecurso, cantidadRecurso) VALUES (2,'Pizarras', 5);
INSERT INTO recurso (codigoRecurso,descripcionRecurso, cantidadRecurso) VALUES (3,'Computadoras', 20);

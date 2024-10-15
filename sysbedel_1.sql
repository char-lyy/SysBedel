CREATE SCHEMA sysbedel;
USE sysbedel;

CREATE TABLE Aula (
    numeroAula INT PRIMARY KEY NOT NULL,
    capacidadAula INT NOT NULL,
    ocupada BOOLEAN NOT NULL
);

CREATE TABLE Reserva (
    id INT PRIMARY KEY AUTO_INCREMENT,
    numeroAula INT NOT NULL,
    horaInicio TIME NOT NULL,
    horaFin TIME NOT NULL,
    fechaHoraReserva DATETIME NOT NULL, -- Fecha en el que se realizo la reserva
    fechaActividad DATE NOT NULL, -- Fecha en la que se realiza la actividad
    descripcion VARCHAR(255),
    responsable VARCHAR(255),
    FOREIGN KEY (numeroAula) REFERENCES Aula(numeroAula)
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

ALTER TABLE reserva ADD COLUMN descripcion VARCHAR(255);
ALTER TABLE reserva ADD COLUMN responsable VARCHAR(255);
ALTER TABLE actividad ADD COLUMN responsable VARCHAR(255);
ALTER TABLE Reserva MODIFY idReserva INT AUTO_INCREMENT;


INSERT INTO Aula (numeroAula, capacidadAula, ocupada, responsable, hora, fechaOcupacion) VALUES (31, 30, FALSE, 'Margarita', '19:00:00','2024-11-11');
INSERT INTO Aula (numeroAula, capacidadAula, ocupada) VALUES (22, 30, FALSE);
INSERT INTO Aula (numeroAula, capacidadAula, ocupada) VALUES (23, 30, FALSE);
INSERT INTO Aula (numeroAula, capacidadAula, ocupada) VALUES (24, 30, FALSE);
INSERT INTO Aula (numeroAula, capacidadAula, ocupada) VALUES (25, 30, FALSE);
INSERT INTO Aula (numeroAula, capacidadAula, ocupada) VALUES (26, 30, FALSE);
INSERT INTO Aula (numeroAula, capacidadAula, ocupada) VALUES (27, 30, FALSE);
INSERT INTO Aula (numeroAula, capacidadAula, ocupada) VALUES (28, 30, FALSE);
INSERT INTO Aula (numeroAula, capacidadAula, ocupada) VALUES (29, 30, FALSE);
INSERT INTO Aula (numeroAula, capacidadAula, ocupada) VALUES (30, 30, FALSE);

INSERT INTO Reserva(idReserva, idActividad, numeroAula, confirmada, horaInicio, horaFin, fechaReserva, fechaActividad, responsable) 
VALUES (1, 1, 24, 1, '08:00:00', '10:00:00', '2024-09-23', '2024-09-23', 'Margarita Alvarez');

INSERT INTO Reserva(idReserva, idActividad, numeroAula, confirmada, horaInicio, horaFin, fechaReserva, fechaActividad, responsable) VALUES (2, 1, 24, 1, '8:00:00', '10:00:00', '2024-09-23', '2024-09-23', 'Mirtha Legrand');
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
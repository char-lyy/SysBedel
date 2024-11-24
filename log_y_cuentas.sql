USE sysbedel;

CREATE TABLE cuentas (
    id INT AUTO_INCREMENT PRIMARY KEY,       -- Identificador único
    username VARCHAR(255) NOT NULL UNIQUE,   -- Nombre de usuario único
    password_hash VARCHAR(255) NOT NULL,     -- Contraseña (en texto plano o hash)
    email VARCHAR(255) DEFAULT NULL,         -- Correo electrónico (opcional)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- Fecha y hora de creación
);

CREATE TABLE login_logs (
    id INT AUTO_INCREMENT PRIMARY KEY,       -- Identificador único del log
    user_id INT NOT NULL,                    -- ID del usuario relacionado
    login_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Fecha y hora del intento
    ip_address VARCHAR(45) NOT NULL,         -- Dirección IP desde donde se intenta el login
    status ENUM('SUCCESS', 'FAIL') NOT NULL, -- Estado del intento (éxito o fallo)
    FOREIGN KEY (user_id) REFERENCES cuentas(id) -- Clave foránea hacia la tabla `cuentas`
);

ALTER TABLE cuentas DROP COLUMN username;
ALTER TABLE cuentas ADD UNIQUE (email);

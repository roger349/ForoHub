CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    contraseña VARCHAR(255) NOT NULL,
    nombre_usuario VARCHAR(255) NOT NULL,
    correo_electronico VARCHAR(255) NOT NULL UNIQUE,
    rol VARCHAR(255) NOT NULL
);


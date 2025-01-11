CREATE TABLE topico (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    mensaje  LONGTEXT NOT NULL,
    fecha_creacion_topico VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    categoria VARCHAR(80) NOT NULL
    autor_id BIGINT,
    FOREIGN KEY (autor_id) REFERENCES usuario(id),

);

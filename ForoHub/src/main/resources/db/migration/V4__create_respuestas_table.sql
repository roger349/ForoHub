CREATE TABLE respuestas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    mensaje_respuestas TEXT NOT NULL,
    fecha_creacion_respuestas DATE NOT NULL,
    solucion BOOLEAN NOT NULL,
    topico_id BIGINT,
    FOREIGN KEY (topico_id) REFERENCES topico(id)
);

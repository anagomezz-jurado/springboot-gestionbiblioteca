CREATE DATABASE IF NOT EXISTS biblio_app;
USE biblio_app;

-- Borrar tablas si existen
DROP TABLE IF EXISTS prestamo;
DROP TABLE IF EXISTS libro;
DROP TABLE IF EXISTS socio;

-- Tabla Libro
CREATE TABLE libro (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    autor VARCHAR(255) NOT NULL
);

-- Tabla Socio
CREATE TABLE socio (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    fin_penalizacion DATE
);

-- Tabla Prestamo
CREATE TABLE prestamo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    socio_id BIGINT NOT NULL,
    libro_id BIGINT NOT NULL,
    fecha_inicio DATETIME,
    fecha_fiin DATETIME,
    estado VARCHAR(20) NOT NULL,
    FOREIGN KEY (socio_id) REFERENCES socio(id) ON DELETE CASCADE,
    FOREIGN KEY (libro_id) REFERENCES libro(id) ON DELETE CASCADE
);

-- Datos de prueba para Libro
INSERT INTO libro (titulo, autor) VALUES
('Cien Años de Soledad', 'Gabriel García Márquez'),
('Don Quijote de la Mancha', 'Miguel de Cervantes'),
('La Sombra del Viento', 'Carlos Ruiz Zafón'),
('El Principito', 'Antoine de Saint-Exupéry');

-- Datos de prueba para Socio
INSERT INTO socio (nombre, email, fin_penalizacion) VALUES
('Ana Gómez', 'ana@example.com', NULL),
('Juan Pérez', 'juan@example.com', '2025-12-01'),
('Lucía Martínez', 'lucia@example.com', NULL);

-- Datos de prueba para Prestamo
INSERT INTO prestamo (socio_id, libro_id, fecha_inicio, fecha_fiin, estado) VALUES
(1, 1, '2025-11-01 10:00:00', '2025-11-15 10:00:00', 'ACTIVO'),
(2, 2, '2025-10-20 15:30:00', '2025-11-05 15:30:00', 'DEVUELTO'),
(3, 3, '2025-11-10 12:00:00', '2025-11-24 12:00:00', 'ACTIVO');

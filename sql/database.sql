CREATE USER videojuegos WITH PASSWORD 'videojuegos';
CREATE DATABASE videojuegos OWNER 'videojuegos';
CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    rol VARCHAR(50) NOT NULL CHECK (rol IN ('ADMIN', 'CLIENTE'))
);
CREATE TABLE videojuegos (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    categoria VARCHAR(100) NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    imagen_base64 TEXT
);
CREATE TABLE compras (
    id SERIAL PRIMARY KEY,
    usuario_id INT REFERENCES usuarios(id) ON DELETE CASCADE,
    videojuego_id INT REFERENCES videojuegos(id) ON DELETE CASCADE,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO usuarios (nombre, email, password_hash, rol) VALUES
('Juan Pérez', 'juan.perez@example.com', 'hash_password_1', 'CLIENTE'),
('María Gómez', 'maria.gomez@example.com', 'hash_password_2', 'CLIENTE'),
('Pedro López', 'pedro.lopez@example.com', 'hash_password_3', 'CLIENTE'),
('Ana Martínez', 'ana.martinez@example.com', 'hash_password_4', 'CLIENTE'),
('Carlos Fernández', 'carlos.fernandez@example.com', 'hash_password_5', 'ADMIN'),
('Laura Sánchez', 'laura.sanchez@example.com', 'hash_password_6', 'CLIENTE'),
('Miguel Ramírez', 'miguel.ramirez@example.com', 'hash_password_7', 'CLIENTE'),
('Sofía Ruiz', 'sofia.ruiz@example.com', 'hash_password_8', 'CLIENTE'),
('Javier Torres', 'javier.torres@example.com', 'hash_password_9', 'ADMIN'),
('Elena Morales', 'elena.morales@example.com', 'hash_password_10', 'CLIENTE'),
('Pablo Castro', 'pablo.castro@example.com', 'hash_password_11', 'CLIENTE'),
('Isabel Delgado', 'isabel.delgado@example.com', 'hash_password_12', 'CLIENTE'),
('Daniel Herrera', 'daniel.herrera@example.com', 'hash_password_13', 'CLIENTE'),
('Valeria Vega', 'valeria.vega@example.com', 'hash_password_14', 'CLIENTE'),
('Lucía García', 'lucia.garcia@example.com', 'hash_password_15', 'CLIENTE'),
('David González', 'david.gonzalez@example.com', 'hash_password_16', 'CLIENTE'),
('Sara López', 'sara.lopez@example.com', 'hash_password_17', 'CLIENTE'),
('Alberto Cruz', 'alberto.cruz@example.com', 'hash_password_18', 'CLIENTE'),
('Victoria Blanco', 'victoria.blanco@example.com', 'hash_password_19', 'CLIENTE'),
('Andrés Méndez', 'andres.mendez@example.com', 'hash_password_20', 'CLIENTE');

INSERT INTO videojuegos (nombre, categoria, precio, imagen_base64) VALUES
('The Witcher 3', 'RPG', 39.99, 'base64_string_1'),
('FIFA 23', 'Deportes', 59.99, 'base64_string_2'),
('Call of Duty: Modern Warfare', 'Shooter', 69.99, 'base64_string_3'),
('Minecraft', 'Aventura', 29.99, 'base64_string_4'),
('Fortnite', 'Battle Royale', 0.00, 'base64_string_5'),
('Grand Theft Auto V', 'Acción', 49.99, 'base64_string_6'),
('Red Dead Redemption 2', 'Aventura', 59.99, 'base64_string_7'),
('Cyberpunk 2077', 'RPG', 44.99, 'base64_string_8'),
('Assassin’s Creed Valhalla', 'Aventura', 54.99, 'base64_string_9'),
('Resident Evil Village', 'Horror', 59.99, 'base64_string_10'),
('Super Mario Odyssey', 'Plataformas', 49.99, 'base64_string_11'),
('Animal Crossing: New Horizons', 'Simulación', 59.99, 'base64_string_12'),
('Halo Infinite', 'Shooter', 59.99, 'base64_string_13'),
('Fall Guys', 'Party', 19.99, 'base64_string_14'),
('League of Legends', 'MOBA', 0.00, 'base64_string_15'),
('Apex Legends', 'Battle Royale', 0.00, 'base64_string_16'),
('Destiny 2', 'Shooter', 0.00, 'base64_string_17'),
('God of War', 'Acción', 49.99, 'base64_string_18'),
('The Legend of Zelda: Breath of the Wild', 'Aventura', 59.99, 'base64_string_19'),
('Elden Ring', 'RPG', 69.99, 'base64_string_20');

INSERT INTO compras (usuario_id, videojuego_id, fecha) VALUES
(1, 1, '2025-01-01 10:00:00'),
(1, 3, '2025-01-02 14:30:00'),
(2, 5, '2025-01-03 16:45:00'),
(3, 2, '2025-01-04 09:15:00'),
(4, 8, '2025-01-05 11:25:00'),
(5, 7, '2025-01-06 20:50:00'),
(6, 10, '2025-01-07 18:30:00'),
(7, 4, '2025-01-08 15:10:00'),
(8, 12, '2025-01-09 13:40:00'),
(9, 9, '2025-01-10 17:55:00'),
(10, 6, '2025-01-11 12:20:00'),
(11, 15, '2025-01-12 21:15:00'),
(12, 14, '2025-01-13 07:50:00'),
(13, 18, '2025-01-14 19:35:00'),
(14, 11, '2025-01-15 16:20:00'),
(15, 17, '2025-01-16 14:10:00'),
(16, 13, '2025-01-17 08:45:00'),
(17, 19, '2025-01-18 10:25:00'),
(18, 16, '2025-01-19 22:05:00'),
(19, 20, '2025-01-20 06:30:00');

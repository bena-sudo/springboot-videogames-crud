CREATE USER videojuegos WITH PASSWORD 'videojuegos';
CREATE DATABASE videojuegos OWNER videojuegos;
DROP TABLE IF EXISTS usuarios_roles;
DROP TABLE IF EXISTS compras;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS usuarios;
DROP TABLE IF EXISTS docvideojuegos;
DROP TABLE IF EXISTS videojuegos;
-- Crea la tabla videojuegos
CREATE TABLE IF NOT EXISTS videojuegos (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    categoria VARCHAR(100) NOT NULL,
    precio DECIMAL(10, 2) NOT NULL
);
-- Crea la tabla usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    nickname VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);
-- Crea la tabla roles
CREATE TABLE IF NOT EXISTS roles (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL
);
-- Crea la tabla compras
CREATE TABLE IF NOT EXISTS compras (
    id SERIAL PRIMARY KEY,
    usuario_id INT REFERENCES usuarios(id) ON DELETE CASCADE,
    videojuego_id INT REFERENCES videojuegos(id) ON DELETE CASCADE,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- Crea la tabla intermedia usuarios_roles
CREATE TABLE IF NOT EXISTS usuarios_roles (
    idUsuario BIGINT NOT NULL,
    idRol INTEGER NOT NULL,
    PRIMARY KEY (idUsuario, idRol),
    CONSTRAINT usuarios_roles_fk_usuarios FOREIGN KEY (idUsuario) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT usuarios_roles_fk_roles FOREIGN KEY (idRol) REFERENCES roles(id) ON DELETE CASCADE
);
CREATE TABLE docvideojuegos (
    id SERIAL PRIMARY KEY,
    videojuego_id INT NOT NULL REFERENCES videojuegos(id) ON DELETE CASCADE,
    nombre_fichero VARCHAR(255) NOT NULL,
    comentario TEXT,
    base64_documento TEXT,
    extension_documento VARCHAR(5),
    content_type_documento VARCHAR(50)
);
-- Inserta usuarios
INSERT INTO usuarios (nombre, nickname, email, password)
VALUES (
        'Juan Pérez',
        'juanp',
        'juan.perez@example.com',
        'hash_password_1'
    ),
    (
        'María Gómez',
        'anag',
        'maria.gomez@example.com',
        'hash_password_2'
    ),
    (
        'Pedro López',
        'pedrol',
        'pedro.lopez@example.com',
        'hash_password_3'
    ),
    (
        'Ana Martínez',
        'anam',
        'ana.martinez@example.com',
        'hash_password_4'
    ),
    (
        'Carlos Fernández',
        'carlosf',
        'carlos.fernandez@example.com',
        'hash_password_5'
    ),
    (
        'Laura Sánchez',
        'lauras',
        'laura.sanchez@example.com',
        'hash_password_6'
    ),
    (
        'Miguel Ramírez',
        'migueld',
        'miguel.ramirez@example.com',
        'hash_password_7'
    ),
    (
        'Sofía Ruiz',
        'sofiar',
        'sofia.ruiz@example.com',
        'hash_password_8'
    ),
    (
        'Javier Torres',
        'javiert',
        'javier.torres@example.com',
        'hash_password_9'
    ),
    (
        'Elena Morales',
        'elenam',
        'elena.morales@example.com',
        'hash_password_10'
    ),
    (
        'Pablo Castro',
        'pabloc',
        'pablo.castro@example.com',
        'hash_password_11'
    ),
    (
        'Isabel Delgado',
        'isabeld',
        'isabel.delgado@example.com',
        'hash_password_12'
    ),
    (
        'Daniel Herrera',
        'danielh',
        'daniel.herrera@example.com',
        'hash_password_13'
    ),
    (
        'Valeria Vega',
        'valeriav',
        'valeria.vega@example.com',
        'hash_password_14'
    ),
    (
        'Lucía García',
        'luciag',
        'lucia.garcia@example.com',
        'hash_password_15'
    ),
    (
        'David González',
        'davidg',
        'david.gonzalez@example.com',
        'hash_password_16'
    ),
    (
        'Sara López',
        'saral',
        'sara.lopez@example.com',
        'hash_password_17'
    ),
    (
        'Alberto Cruz',
        'albertoc',
        'alberto.cruz@example.com',
        'hash_password_18'
    ),
    (
        'Victoria Blanco',
        'victoriab',
        'victoria.blanco@example.com',
        'hash_password_19'
    ),
    (
        'Andrés Méndez',
        'andresm',
        'andres.mendez@example.com',
        'hash_password_20'
    );
-- Inserta videojuegos
INSERT INTO videojuegos (nombre, categoria, precio)
VALUES ('The Witcher 3', 'RPG', 39.99),
    ('FIFA 23', 'Deportes', 59.99),
    (
        'Call of Duty: Modern Warfare',
        'Shooter',
        69.99
    ),
    (
        'Minecraft',
        'Aventura',
        29.99
    ),
    (
        'Fortnite',
        'Battle Royale',
        0.00
    ),
    (
        'Grand Theft Auto V',
        'Acción',
        49.99
    ),
    (
        'Red Dead Redemption 2',
        'Aventura',
        59.99
    ),
    (
        'Cyberpunk 2077',
        'RPG',
        44.99
    ),
    (
        'Assassin’s Creed Valhalla',
        'Aventura',
        54.99
    ),
    (
        'Resident Evil Village',
        'Horror',
        59.99
    ),
    (
        'Super Mario Odyssey',
        'Plataformas',
        49.99
    ),
    (
        'Animal Crossing: New Horizons',
        'Simulación',
        59.99
    ),
    (
        'Halo Infinite',
        'Shooter',
        59.99
    ),
    ('Fall Guys', 'Party', 19.99),
    (
        'League of Legends',
        'MOBA',
        0.00
    ),
    (
        'Apex Legends',
        'Battle Royale',
        0.00
    ),
    ('Destiny 2', 'Shooter', 0.00),
    (
        'God of War',
        'Acción',
        49.99
    ),
    (
        'The Legend of Zelda: Breath of the Wild',
        'Aventura',
        59.99
    ),
    ('Elden Ring', 'RPG', 69.99);
-- Inserta compras
INSERT INTO compras (usuario_id, videojuego_id, fecha)
VALUES (1, 1, '2025-01-01 10:00:00'),
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
-- Inserta roles
INSERT INTO roles (nombre)
VALUES ('CLIENTE'),
    ('ADMIN');
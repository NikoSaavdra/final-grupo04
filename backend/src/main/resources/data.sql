
INSERT INTO tipo (id, nombre) VALUES (1, 'Música');
INSERT INTO tipo (id, nombre) VALUES (2, 'Libro');
INSERT INTO tipo (id, nombre) VALUES (3, 'Película');

-- Inserción de los formatos
INSERT INTO formato (nombre, tipo_id) VALUES ('CD', 1);
INSERT INTO formato (nombre, tipo_id) VALUES ('Vinilo', 1);
INSERT INTO formato (nombre, tipo_id) VALUES ('Digital', 1);

INSERT INTO formato (nombre, tipo_id) VALUES ('Papel', 2);
INSERT INTO formato (nombre, tipo_id) VALUES ('PDF', 2);

INSERT INTO formato (nombre, tipo_id) VALUES ('Blu-ray', 3);
INSERT INTO formato (nombre, tipo_id) VALUES ('DVD', 3);


-- Inserción de un item de tipo "Música" (con formato CD)
INSERT INTO item (titulo, ubicacion, fecha_adquisicion, estado, tipo_id, formato_id, prestamo_id) 
VALUES ('Álbum de Rock', 'Estante A', '2024-02-10', TRUE, 1, 1, NULL);

-- Inserción de un item de tipo "Música" (con formato Vinilo)
INSERT INTO item (titulo, ubicacion, fecha_adquisicion, estado, tipo_id, formato_id, prestamo_id) 
VALUES ('Álbum de Jazz', 'Estante B', '2023-07-20', TRUE, 1, 2, NULL);

-- Inserción de un item de tipo "Música" (con formato Digital)
INSERT INTO item (titulo, ubicacion, fecha_adquisicion, estado, tipo_id, formato_id, prestamo_id) 
VALUES ('Álbum Electrónico', 'Estante C', '2023-11-05', TRUE, 1, 3, NULL);

-- Inserción de un item de tipo "Libro" (con formato Papel)
INSERT INTO item (titulo, ubicacion, fecha_adquisicion, estado, tipo_id, formato_id, prestamo_id) 
VALUES ('Don Quijote de la Mancha', 'Estante 1', '2023-01-10', TRUE, 2, 4, NULL);

-- Inserción de un item de tipo "Libro" (con formato PDF)
INSERT INTO item (titulo, ubicacion, fecha_adquisicion, estado, tipo_id, formato_id, prestamo_id) 
VALUES ('1984', 'Estante 2', '2024-03-01', TRUE, 2, 5, NULL);

-- Inserción de un item de tipo "Película" (con formato Blu-ray)
INSERT INTO item (titulo, ubicacion, fecha_adquisicion, estado, tipo_id, formato_id, prestamo_id) 
VALUES ('Inception', 'Estante 3', '2023-10-15', TRUE, 3, 6, NULL);

-- Inserción de un item de tipo "Película" (con formato DVD)
INSERT INTO item (titulo, ubicacion, fecha_adquisicion, estado, tipo_id, formato_id, prestamo_id) 
VALUES ('Avatar', 'Estante 4', '2024-01-10', TRUE, 3, 7, NULL);



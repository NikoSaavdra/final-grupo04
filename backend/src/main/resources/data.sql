-- Inserción de los tipos sin IDs
INSERT INTO tipo (nombre) VALUES ('Música');
INSERT INTO tipo (nombre) VALUES ('Libro');
INSERT INTO tipo (nombre) VALUES ('Película');


-- Inserción de los formatos
INSERT INTO formato (id, nombre) VALUES (1, 'CD');
INSERT INTO formato (id, nombre) VALUES (2, 'Vinilo');
INSERT INTO formato (id, nombre) VALUES (3, 'Digital');
INSERT INTO formato (id, nombre) VALUES (4, 'Papel');
INSERT INTO formato (id, nombre) VALUES (5, 'PDF');
INSERT INTO formato (id, nombre) VALUES (6, 'Blu-ray');
INSERT INTO formato (id, nombre) VALUES (7, 'DVD');

-- Asociaciones en la tabla intermedia
INSERT INTO tipo_formato (tipo_id, formato_id) VALUES (1, 1); -- Música -> CD
INSERT INTO tipo_formato (tipo_id, formato_id) VALUES (1, 2); -- Música -> Vinilo
INSERT INTO tipo_formato (tipo_id, formato_id) VALUES (1, 3); -- Música -> Digital

INSERT INTO tipo_formato (tipo_id, formato_id) VALUES (2, 4); -- Libro -> Papel
INSERT INTO tipo_formato (tipo_id, formato_id) VALUES (2, 5); -- Libro -> PDF

INSERT INTO tipo_formato (tipo_id, formato_id) VALUES (3, 6); -- Película -> Blu-ray
INSERT INTO tipo_formato (tipo_id, formato_id) VALUES (3, 7); -- Película -> DVD



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



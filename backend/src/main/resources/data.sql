
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




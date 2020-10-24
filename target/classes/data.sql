DROP TABLE IF EXISTS personas;

CREATE TABLE personas (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  nombre VARCHAR(250) NOT NULL,
  apellido VARCHAR(250) NOT NULL,
  procesado VARCHAR(250) DEFAULT NULL
);

INSERT INTO personas (nombre, apellido, procesado) VALUES
  ('Jeisson', 'Ruiz', 'false'),
  ('Juan', 'Diaz', 'false'),
  ('Daniel', 'Gomez', 'false');
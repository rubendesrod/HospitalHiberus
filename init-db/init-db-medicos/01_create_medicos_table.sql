CREATE TABLE IF NOT EXISTS medicos (
  dni VARCHAR(20) PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  especialidad VARCHAR(100) NOT NULL,
  telefono VARCHAR(20) NOT NULL,
  horario VARCHAR(100) NOT NULL,
  sala VARCHAR(50) NOT NULL
);
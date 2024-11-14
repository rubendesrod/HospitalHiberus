-- Definir el tipo ENUM para el campo estado
CREATE TYPE mis_estados AS ENUM('Programada', 'Completada', 'Cancelada');

-- Crear la tabla citas
CREATE TABLE IF NOT EXISTS citas (
  id_cita INT AUTO_INCREMENT PRIMARY KEY,
  id_paciente VARCHAR(15) NOT NULL,
  id_medico VARCHAR(15) NOT NULL,
  fechaHora DATE NOT NULL,
  motivo VARCHAR(200) NOT NULL,
  estado mis_estados NOT NULL
);
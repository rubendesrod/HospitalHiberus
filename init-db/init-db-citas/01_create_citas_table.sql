-- Definir el tipo ENUM para el campo estado
CREATE TYPE mis_estados AS ENUM('programada', 'completada', 'cancelada');

-- Crear la tabla citas
CREATE TABLE IF NOT EXISTS citas (
    id_cita SERIAL PRIMARY KEY, -- Cambiado a SERIAL para autoincremento
    id_paciente VARCHAR(15) NOT NULL,
    id_medico VARCHAR(15) NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    motivo VARCHAR(200) NOT NULL,
    estado varchar(20) NOT NULL
);

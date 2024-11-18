-- Definir el tipo ENUM para el campo estado
CREATE TYPE mis_estados AS ENUM('pendiente', 'pagado');

-- Crear la tabla citas
CREATE TABLE IF NOT EXISTS facturas (
  id_factura INT AUTO_INCREMENT PRIMARY KEY,
  id_medico VARCHAR(15) NOT NULL,
  fechaEmision DATE NOT NULL,
  totalPagar INTEGER NOT NULL,
  estado mis_estados NOT NULL
  fechaPago  DATE,
);
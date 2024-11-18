CREATE TABLE IF NOT EXISTS facturas (
  id_factura INT AUTO_INCREMENT PRIMARY KEY,
  id_medico VARCHAR(15) NOT NULL,
  fechaEmision DATE NOT NULL,
  totalPagar INT NOT NULL,
  estado ENUM('pendiente', 'pagado') NOT NULL,
  fechaPago DATE
);

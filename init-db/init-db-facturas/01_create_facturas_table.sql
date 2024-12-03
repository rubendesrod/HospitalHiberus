CREATE TABLE IF NOT EXISTS facturas (
  id_factura INT AUTO_INCREMENT PRIMARY KEY,
  id_medico VARCHAR(15) NOT NULL,
  fecha_emision DATE NOT NULL,
  total_pagar INT NOT NULL,
  estado ENUM('pendiente', 'pagado') NOT NULL,
  fecha_pago DATE NULL
);

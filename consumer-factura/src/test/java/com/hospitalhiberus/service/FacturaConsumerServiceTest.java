package com.hospitalhiberus.service;

import com.hospitalhiberus.avro.FacturaKey;
import com.hospitalhiberus.avro.FacturaValue;
import com.hospitalhiberus.model.ESTADOS;
import com.hospitalhiberus.model.Factura;
import com.hospitalhiberus.repository.FacturaRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Con esta extension no tengo que crear un @BeforeEach
@ExtendWith(MockitoExtension.class)
public class FacturaConsumerServiceTest {

    @Mock
    private FacturaRepository repository;

    @InjectMocks
    private FacturaConsumerService consumerService;

    @Test
    @DisplayName("Test 01 - Verificar que la factura se guarda correctamente en el repositorio")
    void testConsumoExitoso() {
        // Arrange
        FacturaKey key = new FacturaKey("123");
        FacturaValue value = new FacturaValue();
        value.setTotalPagar(100);
        value.setFechaEmision("2023-11-15");
        value.setIdMedico("MED123");
        ConsumerRecord<FacturaKey, FacturaValue> record = new ConsumerRecord<>("facturas", 0, 0, key, value);

        // Act
        consumerService.consume(record);

        // Assert
        verify(repository, times(1)).save(any(Factura.class));
    }

    @Test
    @DisplayName("Test 02 - Test de que la factura avro se mapea correctamente a un Objeto factura")
    void testMapearFactura() {
        FacturaValue value = new FacturaValue();
        value.setTotalPagar(300);
        value.setFechaEmision("2023-11-17");
        value.setIdMedico("MED789");

        Factura factura = consumerService.mapearFactura(value);

        assert factura.getTotalPagar() == 300.0;
        assert factura.getFechaEmision().equals(LocalDate.parse("2023-11-17"));
        assert factura.getIdMedico().equals("MED789");
        assert factura.getEstado() == ESTADOS.pendiente;
    }
}

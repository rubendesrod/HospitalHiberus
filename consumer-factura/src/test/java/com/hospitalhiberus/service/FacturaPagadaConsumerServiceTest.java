package com.hospitalhiberus.service;

import com.hospitalhiberus.avro.FacturaPagadaKey;
import com.hospitalhiberus.avro.FacturaPagadaValue;
import com.hospitalhiberus.model.ESTADOS;
import com.hospitalhiberus.model.Factura;
import com.hospitalhiberus.repository.FacturaRepository;
import com.hospitalhiberus.service.FacturaPagadaConsumerService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Con esta extension no tengo que crear un @BeforeEach
@ExtendWith(MockitoExtension.class)
public class FacturaPagadaConsumerServiceTest {

    @Mock
    private FacturaRepository repository;

    @InjectMocks
    private FacturaPagadaConsumerService consumerService;

    @Test
    @DisplayName("Test 01 - Pago de una factura")
    void testPagoFacturaCorrecto() {
        // Arrange
        FacturaPagadaKey key = new FacturaPagadaKey(123);
        FacturaPagadaValue value = new FacturaPagadaValue();
        ConsumerRecord<FacturaPagadaKey, FacturaPagadaValue> record = new ConsumerRecord<>("facturasPagadas", 0, 0, key, value);

        Factura existingFactura = new Factura();
        existingFactura.setIdFactura(123);
        existingFactura.setEstado(ESTADOS.pendiente);

        when(repository.findById(123)).thenReturn(Optional.of(existingFactura));

        // Act
        consumerService.consume(record);

        // Assert
        verify(repository, times(1)).save(existingFactura);
        assert existingFactura.getEstado() == ESTADOS.pagado;
        assert existingFactura.getFechaPago() != null;
    }

    @Test
    @DisplayName("Test 02 - No se encuentra la factura recibida")
    void testFacturaNoEncontrada() {
        // Arrange
        FacturaPagadaKey key = new FacturaPagadaKey(456);
        FacturaPagadaValue value = new FacturaPagadaValue();
        ConsumerRecord<FacturaPagadaKey, FacturaPagadaValue> record = new ConsumerRecord<>("facturasPagadas", 0, 0, key, value);

        when(repository.findById(456)).thenReturn(Optional.empty());

        // Act
        consumerService.consume(record);

        // Assert
        verify(repository, never()).save(any(Factura.class));
    }

}


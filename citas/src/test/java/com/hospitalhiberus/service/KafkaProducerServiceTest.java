package com.hospitalhiberus.service;

import com.hospitalhiberus.avro.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class KafkaProducerServiceTest {

    @Mock
    private KafkaTemplate<HistorialMedicoKey, HistorialMedicoValue> kafkaTemplateHisotorial;

    @Mock
    private KafkaTemplate<FacturaKey, FacturaValue> kafkaTemplateFactura;

    @InjectMocks
    private KafkaProducerService kafkaProducerService;

    @Test
    @DisplayName("Test 01 - Comprobacion que el historialMedico se manda al topic")
    void testEnviarHistorialMedico() {
        String topic = "historialMedico";
        HistorialMedicoValue historialMedicoValue = HistorialMedicoValue.newBuilder()
                .setIdPaciente("123")
                .setFecha("2023-11-27")
                .setVisitas(List.of())
                .build();

        kafkaProducerService.enviarHistorialMedico(topic, historialMedicoValue);

        verify(kafkaTemplateHisotorial, times(1)).send(any(), any(), any());
    }

    @Test
    @DisplayName("Test 02 - Comprobacion de que la factura se manda al topic")
    void testEnviarFactura() {
        String topic = "facturas";
        FacturaValue facturaValue = FacturaValue.newBuilder()
                .setIdMedico("456")
                .setFechaEmision("2023-11-27")
                .setTotalPagar(100)
                .setEstado(com.hospitalhiberus.avro.ESTADOS.pendiente)
                .build();

        kafkaProducerService.enviarFactura(topic, facturaValue);

        verify(kafkaTemplateFactura, times(1)).send(any(), any(), any());
    }
}

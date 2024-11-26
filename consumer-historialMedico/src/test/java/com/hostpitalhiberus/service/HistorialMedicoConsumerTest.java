package com.hostpitalhiberus.service;

import com.hospitalhiberus.avro.HistorialMedicoKey;
import com.hospitalhiberus.avro.HistorialMedicoValue;
import com.hospitalhiberus.mapper.Mapper;
import com.hospitalhiberus.model.HistorialMedico;
import com.hospitalhiberus.model.Visita;
import com.hospitalhiberus.repository.HistorialMedicoRepository;
import com.hospitalhiberus.service.HistorialMedicoConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

// Con esta extension no tengo que crear un @BeforeEach
@ExtendWith(MockitoExtension.class)
public class HistorialMedicoConsumerTest {

    @Mock
    private HistorialMedicoRepository repository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private HistorialMedicoConsumer consumer;

    @Test
    @DisplayName("Test 01 - save en la DB cuando existe el historial")
    void testGuardarCuandoElHistorialExiste() {
        // Arrange
        HistorialMedicoKey key = new HistorialMedicoKey("123");
        HistorialMedicoValue value = new HistorialMedicoValue();
        ConsumerRecord<HistorialMedicoKey, HistorialMedicoValue> record = new ConsumerRecord<>("historialMedico", 0, 0, key, value);

        HistorialMedico existingHistorial = new HistorialMedico();
        existingHistorial.setIdPaciente("123");
        existingHistorial.setVisitas(new ArrayList<>());

        List<Visita> visitasMapeadas = new ArrayList<>();
        visitasMapeadas.add(new Visita());

        when(repository.findByIdPaciente("123")).thenReturn(existingHistorial);
        when(mapper.mapperVisitas(value)).thenReturn(visitasMapeadas);

        consumer.consume(record);

        verify(repository, times(1)).save(existingHistorial);
        verify(mapper, times(1)).mapperVisitas(value);
    }

    @Test
    @DisplayName("Test 02 - save en la DB cuando no existe el historial")
    void testGuardarCuandoNoExisteEnDB() {
        // Arrange
        HistorialMedicoKey key = new HistorialMedicoKey("456");
        HistorialMedicoValue value = new HistorialMedicoValue();
        ConsumerRecord<HistorialMedicoKey, HistorialMedicoValue> record = new ConsumerRecord<>("historialMedico", 0, 0, key, value);

        List<Visita> visitasMapeadas = new ArrayList<>();
        visitasMapeadas.add(new Visita());

        HistorialMedico nuevoHistorial = new HistorialMedico();
        nuevoHistorial.setIdPaciente("456");
        nuevoHistorial.setVisitas(visitasMapeadas);

        when(repository.findByIdPaciente("456")).thenReturn(null);
        when(mapper.mapperVisitas(value)).thenReturn(visitasMapeadas);
        when(mapper.mapperHistorial(value, visitasMapeadas)).thenReturn(nuevoHistorial);

        consumer.consume(record);

        verify(repository, times(1)).save(nuevoHistorial);
        verify(mapper, times(1)).mapperVisitas(value);
        verify(mapper, times(1)).mapperHistorial(value, visitasMapeadas);
    }
}

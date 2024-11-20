package com.hospitalhiberus.service;

import com.hospitalhiberus.avro.HistorialMedico;
import com.hospitalhiberus.avro.Visita;
import com.hospitalhiberus.mapper.Mapper;
import com.hospitalhiberus.repository.HistorialMedicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HistorialMedicoConsumerTest {

    @Mock
    private HistorialMedicoRepository repository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private HistorialMedicoConsumer consumer;

    private HistorialMedico historialAvro;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configuración de un objeto HistorialMedico de prueba (Avro)
        historialAvro = HistorialMedico.newBuilder()
                .setIdHistorial(1)
                .setIdPaciente("paciente-1")
                .setFecha("2024-11-20")
                .setVisitas(List.of(
                        crearVisita("2024-11-20", "10:30", "Consulta general", List.of("Paracetamol"))
                ))
                .build();
    }

    @Test
    void testConsumeNuevoHistorial() {
        // Configurar el mock para devolver null al buscar el historial existente
        when(repository.findByIdPaciente("paciente-1")).thenReturn(null);

        // Configurar el mapeo de visitas
        List<com.hospitalhiberus.model.Visita> visitasMapeadas = List.of(
                crearModeloVisita("2024-11-20", "10:30", "Consulta general", List.of("Paracetamol"))
        );
        when(mapper.mapperVisitas(historialAvro)).thenReturn(visitasMapeadas);

        // Configurar el mapeo de un nuevo historial médico
        com.hospitalhiberus.model.HistorialMedico nuevoHistorial = crearModeloHistorial("paciente-1", visitasMapeadas);
        when(mapper.mapperHistorial(historialAvro, visitasMapeadas)).thenReturn(nuevoHistorial);

        // Ejecutar el método consume
        consumer.consume(historialAvro);

        // Verificar que se guardó un nuevo historial médico
        verify(repository).save(nuevoHistorial);

        // Capturar el historial guardado y verificar los detalles
        ArgumentCaptor<com.hospitalhiberus.model.HistorialMedico> captor = ArgumentCaptor.forClass(com.hospitalhiberus.model.HistorialMedico.class);
        verify(repository).save(captor.capture());
        com.hospitalhiberus.model.HistorialMedico historialGuardado = captor.getValue();

        assertEquals("paciente-1", historialGuardado.getIdPaciente());
        assertEquals(1, historialGuardado.getVisitas().size());
    }

    @Test
    void testConsumeActualizarHistorialExistente() {
        // Configurar el mock para devolver un historial existente
        com.hospitalhiberus.model.HistorialMedico historialExistente = crearModeloHistorial("paciente-1", List.of());
        when(repository.findByIdPaciente("paciente-1")).thenReturn(historialExistente);

        // Configurar el mapeo de visitas
        List<com.hospitalhiberus.model.Visita> visitasMapeadas = List.of(
                crearModeloVisita("2024-11-20", "10:30", "Consulta general", List.of("Paracetamol"))
        );
        when(mapper.mapperVisitas(historialAvro)).thenReturn(visitasMapeadas);

        // Ejecutar el método consume
        consumer.consume(historialAvro);

        // Verificar que las visitas se añadieron al historial existente
        assertEquals(1, historialExistente.getVisitas().size());
        verify(repository).save(historialExistente);
    }

    @Test
    void testConsumeConError() {
        // Configurar un mock que lance una excepción durante el procesamiento
        when(mapper.mapperVisitas(historialAvro)).thenThrow(new RuntimeException("Error simulado"));

        // Ejecutar el método consume y verificar que no lanza excepciones hacia afuera
        assertDoesNotThrow(() -> consumer.consume(historialAvro));

        // Verificar que no se realizó ningún guardado en el repositorio
        verify(repository, never()).save(any());
    }

    // Métodos auxiliares para crear objetos de prueba (Avro y Modelos)
    private Visita crearVisita(String fecha, String hora, String motivo, List<String> tratamiento) {
        return Visita.newBuilder()
                .setFechaVisita(fecha)
                .setHora(hora)
                .setMotivo(motivo)
                .setTratamiento(tratamiento)
                .build();
    }

    private com.hospitalhiberus.model.Visita crearModeloVisita(String fecha, String hora, String motivo, List<String> tratamiento) {
        com.hospitalhiberus.model.Visita visita = new com.hospitalhiberus.model.Visita();
        visita.setFechaVisita(fecha);
        visita.setHora(hora);
        visita.setMotivo(motivo);
        visita.setTratamiento(tratamiento);
        return visita;
    }

    private com.hospitalhiberus.model.HistorialMedico crearModeloHistorial(String idPaciente, List<com.hospitalhiberus.model.Visita> visitas) {
        com.hospitalhiberus.model.HistorialMedico historial = new com.hospitalhiberus.model.HistorialMedico();
        historial.setIdPaciente(idPaciente);
        historial.setFecha("2024-11-20");
        historial.setVisitas(visitas);
        return historial;
    }
}

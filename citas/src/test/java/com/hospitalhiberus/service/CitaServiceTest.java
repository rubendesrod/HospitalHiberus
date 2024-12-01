package com.hospitalhiberus.service;

import com.hospitalhiberus.avro.FacturaValue;
import com.hospitalhiberus.avro.HistorialMedicoValue;
import com.hospitalhiberus.model.Cita;
import com.hospitalhiberus.model.ESTADOS;
import com.hospitalhiberus.repository.CitaRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CitaServiceTest {

    @Mock
    private CitaRepository repository;

    @Mock
    private ValidacionService validacionService;

    @Mock
    private KafkaProducerService kafkaService;

    @InjectMocks
    private CitaService citaService;

    @Test
    @DisplayName("Test 01 - Obtener todas las citas")
    void testObtenerTodasLasCitas() {
        List<Cita> citas = List.of(new Cita());
        when(repository.findAll()).thenReturn(citas);

        List<Cita> result = citaService.obtenerTodasLasCitas();

        assertEquals(citas, result);
    }

    @Test
    @DisplayName("Test 02 - Obtener las citas de un paciente")
    void testObtenerCitasPorIdPaciente() {
        String idPaciente = "123";
        List<Cita> citas = List.of(new Cita());
        doNothing().when(validacionService).verificarExistenciaPaciente(idPaciente);
        when(repository.findCitaByIdPaciente(idPaciente)).thenReturn(citas);

        List<Cita> result = citaService.obtenerCitasPorIdPaciente(idPaciente);

        assertEquals(citas, result);
    }

    @Test
    @DisplayName("Test 03 - Crear una cita")
    void testCrearCita() {
        Cita cita = new Cita();
        cita.setIdPaciente("123");
        cita.setIdMedico("456");
        doNothing().when(validacionService).verificarExistenciaMedico(cita.getIdMedico());
        doNothing().when(validacionService).verificarExistenciaPaciente(cita.getIdPaciente());
        when(repository.save(cita)).thenReturn(cita);

        Cita result = citaService.crearCita(cita);

        assertEquals(cita, result);
    }

    @Test
    @DisplayName("Test 04 - Completar una cita")
    void testCompletarCita() {
        Integer idCita = 789;
        List<String> tratamiento = List.of("Tratamiento A");
        Cita cita = new Cita();
        cita.setId(idCita);
        cita.setIdPaciente("123");
        cita.setIdMedico("456");
        cita.setFecha(LocalDate.now());
        cita.setHora(LocalTime.now());
        cita.setMotivo("Consulta");
        when(validacionService.obtenerCitaPorId(String.valueOf(idCita))).thenReturn(cita);
        when(repository.save(cita)).thenReturn(cita);

        Cita result = citaService.completarCita(String.valueOf(idCita), tratamiento);

        assertEquals(ESTADOS.completada, result.getEstado());
        // Comprobacion que a los servicios de kafka se les llama exactamente una vez
        verify(kafkaService, times(1)).enviarHistorialMedico(any(), any(HistorialMedicoValue.class));
        verify(kafkaService, times(1)).enviarFactura(any(), any(FacturaValue.class));
    }

    @Test
    @DisplayName("Test 05 - Completa una cita con tratamiento vacío")
    void testCompletarCitaConTratamientoVacio() {
        Integer idCita = 789;
        when(validacionService.obtenerCitaPorId(String.valueOf(idCita))).thenReturn(new Cita());

        assertThrows(IllegalArgumentException.class, () -> citaService.completarCita(String.valueOf(idCita), List.of()));
    }

    @Test
    @DisplayName("Test 06 - Cancelar una cita")
    void testCancelarCita() {
        Integer idCita = 101;
        Cita cita = new Cita();
        cita.setId(idCita);
        when(validacionService.obtenerCitaPorId(String.valueOf(idCita))).thenReturn(cita);
        when(repository.save(cita)).thenReturn(cita);

        Cita result = citaService.cancelarCita(String.valueOf(idCita));

        assertEquals(ESTADOS.cancelada, result.getEstado());
    }
}

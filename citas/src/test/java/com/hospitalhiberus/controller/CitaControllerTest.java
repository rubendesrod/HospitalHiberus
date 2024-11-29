package com.hospitalhiberus.controller;

import com.hospitalhiberus.model.Cita;
import com.hospitalhiberus.model.ESTADOS;
import com.hospitalhiberus.service.CitaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CitaControllerTest {

    @Mock
    private CitaService citaService;

    @InjectMocks
    private CitaController citaController;

    private List<Cita> citas;

    @BeforeEach
    void setup() {
        Cita cita1 = new Cita();
        cita1.setId(1);
        cita1.setIdPaciente("12345678A");
        cita1.setIdMedico("1111");
        cita1.setFecha(LocalDate.of(2023, 10, 20));
        cita1.setHora(LocalTime.of(10, 30));
        cita1.setMotivo("Consulta general");
        cita1.setEstado(ESTADOS.programada);

        Cita cita2 = new Cita();
        cita2.setId(2);
        cita2.setIdPaciente("87654321B");
        cita2.setIdMedico("2222");
        cita2.setFecha(LocalDate.of(2023, 10, 22));
        cita2.setHora(LocalTime.of(11, 0));
        cita2.setMotivo("Control de rutina");
        cita2.setEstado(ESTADOS.completada);

        citas = List.of(cita1, cita2);
    }

    @Test
    @DisplayName("Test 01 - para obtener todas las citas")
    void testObtenerTodasLasCitas() {
        when(citaService.obtenerTodasLasCitas()).thenReturn(citas);

        ResponseEntity<List<Cita>> response = citaController.obtenerTodasLasCitas();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    @DisplayName("Test 02 - para obtener citas por ID de paciente")
    void testObtenerCitasPorIdPaciente() {
        when(citaService.obtenerCitasPorIdPaciente("12345678A")).thenReturn(List.of(citas.get(0)));

        ResponseEntity<List<Cita>> response = citaController.obtenerCitasPorIdPaciente("12345678A");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("12345678A", response.getBody().get(0).getIdPaciente());

        when(citaService.obtenerCitasPorIdPaciente("99999999Z")).thenReturn(List.of());
        response = citaController.obtenerCitasPorIdPaciente("99999999Z");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("Test 03 - para obtener citas por ID de medico")
    void testObtenerCitasPorIdMedico() {
        when(citaService.obtenerCitasPorIdMedico("1111")).thenReturn(List.of(citas.get(0)));

        ResponseEntity<List<Cita>> response = citaController.obtenerCitasPorIdMedico("1111");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("1111", response.getBody().get(0).getIdMedico());

        when(citaService.obtenerCitasPorIdMedico("9999")).thenReturn(List.of());
        response = citaController.obtenerCitasPorIdMedico("9999");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("Test 04 - para obtener citas por ID de medico y estado")
    void testObtenerCitasPorIdMedicoYEstado() {
        when(citaService.obtenerCitasPorIdMedicoYEstado("1111", ESTADOS.programada)).thenReturn(List.of(citas.get(0)));

        ResponseEntity<List<Cita>> response = citaController.obtenerCitasPorIdMedicoYEstado("1111", ESTADOS.programada);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(ESTADOS.programada, response.getBody().get(0).getEstado());

        when(citaService.obtenerCitasPorIdMedicoYEstado("1111", ESTADOS.cancelada)).thenReturn(List.of());
        response = citaController.obtenerCitasPorIdMedicoYEstado("1111", ESTADOS.cancelada);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("Test 05 - para crear una cita")
    void testCrearCita() {
        Cita nuevaCita = citas.get(0);

        when(citaService.crearCita(nuevaCita)).thenReturn(nuevaCita);

        ResponseEntity<Cita> response = citaController.crearCita(nuevaCita);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(nuevaCita.getId(), response.getBody().getId());
    }

    @Test
    @DisplayName("Test 06 - para completar una cita")
    void testCompletarCita() {
        List<String> tratamiento = List.of("Tratamiento 1", "Tratamiento 2");
        Cita citaCompletada = new Cita();
        citaCompletada.setId(1);
        citaCompletada.setIdPaciente("12345678A");
        citaCompletada.setIdMedico("1111");
        citaCompletada.setFecha(LocalDate.of(2023, 10, 20));
        citaCompletada.setHora(LocalTime.of(10, 30));
        citaCompletada.setMotivo("Consulta general");
        citaCompletada.setEstado(ESTADOS.completada);

        when(citaService.completarCita("1", tratamiento)).thenReturn(citaCompletada);

        ResponseEntity<Cita> response = citaController.completarCita("1", tratamiento);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ESTADOS.completada, response.getBody().getEstado());
    }

    @Test
    @DisplayName("Test 07 - para cancelar una cita")
    void testCancelarCita() {
        Cita citaCancelada = new Cita();
        citaCancelada.setId(1);
        citaCancelada.setIdPaciente("12345678A");
        citaCancelada.setIdMedico("1111");
        citaCancelada.setFecha(LocalDate.of(2023, 10, 20));
        citaCancelada.setHora(LocalTime.of(10, 30));
        citaCancelada.setMotivo("Consulta general");
        citaCancelada.setEstado(ESTADOS.cancelada);

        when(citaService.cancelarCita("1")).thenReturn(citaCancelada);

        ResponseEntity<Cita> response = citaController.cancelarCita("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ESTADOS.cancelada, response.getBody().getEstado());
    }
}

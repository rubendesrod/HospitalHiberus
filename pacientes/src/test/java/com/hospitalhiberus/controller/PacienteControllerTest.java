package com.hospitalhiberus.controller;

import com.hospitalhiberus.model.Paciente;
import com.hospitalhiberus.service.PacienteService;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PacienteControllerTest {

    @Mock
    private PacienteService pacienteService;

    @InjectMocks
    private PacienteController pacienteController;

    private List<Paciente> pacientes;

    @BeforeEach
    void setup() {
        LocalDate fechaNac = LocalDate.of(2002, 8, 4);
        pacientes = List.of(
                new Paciente("12543674T", "Ruben", "Descalzo Rodriguez", fechaNac, "rubendes@hiberus.com", "C/ Maldonado 34, 5 Izq"),
                new Paciente("13418945K", "Fernando", "Manson Ruiz", fechaNac, "fernandoman@hiberus.com", "C/ Colon 5, Bajo B")
        );
    }

    @Test
    @DisplayName("Test 01 - para obtener todos los pacientes")
    void testGetAllPacientes() {
        // Configuracion del comportamiento del servicio
        when(pacienteService.obtenerTodosLosPacientes()).thenReturn(pacientes);

        // LLamo al metodo del controlador
        ResponseEntity<List<Paciente>> response = pacienteController.getPacientes();

        // Hago las comprobaciones de si devuelve el codigo de estado 200 OK y su tamaño es de 2 pacientes
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    @DisplayName("Test 02 - para obtener un paciente")
    void testGetPaciente() {
        Paciente paciente = pacientes.get(0);

        when(pacienteService.obtenerPacientePorDni("12543674T")).thenReturn(paciente);

        ResponseEntity<Paciente> response = pacienteController.getPaciente("12543674T");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Ruben", response.getBody().getNombre());

        // prueba con un DNI que no existe
        when(pacienteService.obtenerPacientePorDni("12345678A")).thenReturn(null);
        response = pacienteController.getPaciente("12345678A");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    @DisplayName("Test 03 - para crear un paciente")
    void testCreatePaciente() {
        Paciente paciente = pacientes.get(0);

        when(pacienteService.existePacientePorDni(paciente.getDni())).thenReturn(false);
        when(pacienteService.crearPaciente(paciente)).thenReturn(paciente);

        ResponseEntity<Paciente> response = pacienteController.crearPaciente(paciente);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(paciente.getDni(), response.getBody().getDni());

        // Añadir que pasa si cuando se quiere agregar un usuario con el mismo DNI mande un Conflict
        when(pacienteService.existePacientePorDni(paciente.getDni())).thenReturn(true);
        response = pacienteController.crearPaciente(paciente);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    @DisplayName("Test 04 - para actualizar un paciente")
    void testUpdatePaciente() {
        Paciente pacienteExistente = pacientes.get(0);
        Paciente pacienteActualizado = new Paciente("12543674T", "Raul", "Rodriguez Carrion", pacienteExistente.getFechanac(), "rubendes@hiberus.com", "C/ Maldonado 34, 8 Izq");

        when(pacienteService.actualizarPaciente("12543674T", pacienteActualizado)).thenReturn(pacienteActualizado);

        ResponseEntity<Paciente> response = pacienteController.actualizarPaciente("12543674T", pacienteActualizado);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Raul", response.getBody().getNombre());
        assertEquals("Rodriguez Carrion", response.getBody().getApellidos());
        assertEquals("C/ Maldonado 34, 8 Izq", response.getBody().getDireccion());
    }

    @Test
    @DisplayName("Test 05 - para borrar un paciente")
    void testDeletePaciente() {
        when(pacienteService.existePacientePorDni("12543674T")).thenReturn(true);

        ResponseEntity<Void> response = pacienteController.eliminarPaciente("12543674T");

        verify(pacienteService).eliminarPacientePorDni("12543674T");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}

package com.hospitalhiberus.service;

import com.hospitalhiberus.model.Paciente;
import com.hospitalhiberus.repository.PacienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PacienteServiceTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @InjectMocks
    private PacienteService pacienteService;

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
    void testObtenerTodosLosPacientes() {
        // Configuracion del comportamiento del repositorio
        when(pacienteRepository.findAll()).thenReturn(pacientes);

        // LLamo al metodo del servicio
        List<Paciente> resultado = pacienteService.obtenerTodosLosPacientes();

        // Hago las comprobaciones de si el tamaño es de 2 pacientes
        assertEquals(2, resultado.size());
        assertEquals("Ruben", resultado.get(0).getNombre());
        assertEquals("Fernando", resultado.get(1).getNombre());
    }

    @Test
    @DisplayName("Test 02 - para obtener un paciente por DNI")
    void testObtenerPacientePorDni() {
        Paciente paciente = pacientes.get(0);

        when(pacienteRepository.findByDni("12543674T")).thenReturn(paciente);

        Paciente resultado = pacienteService.obtenerPacientePorDni("12543674T");

        assertNotNull(resultado);
        assertEquals("Ruben", resultado.getNombre());
        assertEquals("12543674T", resultado.getDni());
    }

    @Test
    @DisplayName("Test 03 - para crear un paciente")
    void testCrearPaciente() {
        Paciente paciente = pacientes.get(0);

        when(pacienteRepository.save(paciente)).thenReturn(paciente);

        Paciente nuevoPaciente = pacienteService.crearPaciente(paciente);

        assertNotNull(nuevoPaciente);
        assertEquals("Ruben", nuevoPaciente.getNombre());
        assertEquals("12543674T", nuevoPaciente.getDni());
    }

    @Test
    @DisplayName("Test 04 - para actualizar un paciente")
    void testActualizarPaciente() {
        Paciente pacienteExistente = pacientes.get(0);
        Paciente pacienteActualizado = new Paciente("12543674T", "Ruben", "Descalzo Rodriguez", pacienteExistente.getFechanac(), "rubendes@hiberus.com", "C/ Maldonado 34, 8 Izq");

        when(pacienteRepository.findByDni("12543674T")).thenReturn(pacienteExistente);
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(pacienteActualizado);

        Paciente resultado = pacienteService.actualizarPaciente("12543674T", pacienteActualizado);

        assertNotNull(resultado);
        assertEquals("Ruben", resultado.getNombre());
        assertEquals("Descalzo Rodriguez", resultado.getApellidos());
        assertEquals("C/ Maldonado 34, 8 Izq", resultado.getDireccion());
    }

    @Test
    @DisplayName("Test 05 - para eliminar un paciente por DNI")
    void testEliminarPacientePorDni() {
        String dni = "12543674T";

        pacienteService.eliminarPacientePorDni(dni);

        verify(pacienteRepository, times(1)).deleteByDni(dni);
    }
}

package com.hospitalhiberus.controller;

import com.hospitalhiberus.model.Paciente;
import com.hospitalhiberus.repository.PacienteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

// Con esta extension no tengo que crear un @BeforeEach
@ExtendWith(MockitoExtension.class)
class PacienteControllerTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @InjectMocks
    private PacienteController pacienteController;

    @Test
    @DisplayName("Test para obtener todos los pacientes")
    void testGetAllPacientes(){

        // Seteo la fecha del paciente
        LocalDate fechaNac = LocalDate.of(2002, 8, 4);

        Paciente paciente1 = new Paciente("12543674T", "Ruben", "Descalzo Rodriguez", fechaNac, "rubendes@hiberus.com", "C/ Maldonado 34, 5 Izq");
        Paciente paciente2 = new Paciente("13418945K", "Fernando", "Manson Ruiz", fechaNac, "fernandoman@hiberus.com", "C/ Colon 5, Bajo B");

        // Configuracion del comportamiento del repositorio
        when(pacienteRepository.findAll()).thenReturn(Arrays.asList(paciente1, paciente2));

        // LLamo al metodo del controlador y hago un cast ya que devuelvo una list y quiero devolver una response entity para comprobar el status
        ResponseEntity<List<Paciente>> response = pacienteController.getPacientes();

        // Hago las comprobaciones de si devuelve el codigo de estado 200 OK y su tamaño es de 2 pacientes
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());

    }

    @Test
    @DisplayName("Test para obtener un paciente")
    void testGetPaciente(){
        // Seteo la fecha del paciente
        LocalDate fechanac = LocalDate.of(2002, 8, 4);
        Paciente paciente1 = new Paciente("12543674T", "Ruben", "Descalzo Rodriguez", fechanac, "rubendes@hiberus.com", "C/ Maldonado 34, 5 Izq");

        when(pacienteRepository.findByDni("12543674T")).thenReturn(paciente1);

        ResponseEntity<Paciente> response = pacienteController.getPaciente("12543674T");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Ruben", response.getBody().getNombre());

        // prueba con un DNI que no existe
        response = pacienteController.getPaciente("12345678A");

        // Sale 200 ya que devuelve un array vacio
        assertEquals(404, response.getStatusCodeValue());
        assertEquals(null, response.getBody());

    }

    @Test
    @DisplayName("Test para crear un paciente")
    void testCreatePaciente(){

        // Creo un paciente
        LocalDate fechaNac = LocalDate.of(2002, 8, 4);
        Paciente paciente1 = new Paciente("12543674T", "Ruben", "Descalzo Rodriguez", fechaNac, "rubendes@hiberus.com", "C/ Maldonado 34, 5 Izq");

        when(pacienteRepository.save(paciente1)).thenReturn(paciente1);

        ResponseEntity<Paciente> response = pacienteController.crearPaciente(paciente1);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(paciente1.getDni(), response.getBody().getDni());

        // Añadir que pasa si cuando se quiere agregar un usuario con el mismo DNI mande un Conflict

    }

    @Test
    @DisplayName("Test para actualizar un paciente")
    void testUpdatePaciente() {

        // Creo un paciente existente
        LocalDate fechaNac = LocalDate.of(2002, 8, 4);
        Paciente paciente1 = new Paciente("12543674T", "Ruben", "Descalzo Rodriguez", fechaNac, "rubendes@hiberus.com", "C/ Maldonado 34, 5 Izq");

        // Creo un paciente con datos actualizados
        Paciente pacienteUpdate = new Paciente("12543674T", "Raul", "Rodriguez Carrion", fechaNac, "rubendes@hiberus.com", "C/ Maldonado 34, 8 Izq");

        // Simulo que el paciente existe en el repositorio
        when(pacienteRepository.findByDni("12543674T")).thenReturn(paciente1);

        // Simulo que el repositorio guarda el paciente actualizado
        when(pacienteRepository.save(paciente1)).thenReturn(pacienteUpdate);

        // Llamo al método de actualización en el controlador
        ResponseEntity<Paciente> response = pacienteController.actualizarPaciente("12543674T", pacienteUpdate);

        // Verifico que el repositorio guardó el paciente actualizado
        verify(pacienteRepository).save(paciente1);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Raul", response.getBody().getNombre());
        assertEquals("Rodriguez Carrion", response.getBody().getApellidos());
        assertEquals("C/ Maldonado 34, 8 Izq", response.getBody().getDireccion());
    }

    @Test
    @DisplayName("Test para borrar un paciente")
    void testDeletePaciente() {
        // Simulo que el paciente existe en el repositorio
        when(pacienteRepository.existsByDni("12543674T")).thenReturn(true);

        // Llamo al método de eliminación en el controlador
        ResponseEntity<Void> response = pacienteController.eliminarPaciente("12543674T");

        // Verifico que el repositorio intenta eliminar el paciente
        verify(pacienteRepository).deleteByDni("12543674T");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

}

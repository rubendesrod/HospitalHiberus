package com.hospitalhiberus.service;

import com.hospitalhiberus.feign.MedicoClient;
import com.hospitalhiberus.feign.PacienteClient;
import com.hospitalhiberus.model.Cita;
import com.hospitalhiberus.repository.CitaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ValidacionServiceTest {

    @Mock
    private MedicoClient medicoClient;

    @Mock
    private PacienteClient pacienteClient;

    @Mock
    private CitaRepository citaRepository;

    @InjectMocks
    private ValidacionService validacionService;

    @Test
    @DisplayName("Test 01 - Verificar la existencia de un Medico existente")
    void testVerificarExistenciaMedicoExitoso() {
        String dniMedico = "12345678A";
        ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(Map.of(), HttpStatus.OK);
        when(medicoClient.obtenerMedico(dniMedico)).thenReturn(responseEntity);

        validacionService.verificarExistenciaMedico(dniMedico);

        verify(medicoClient, times(1)).obtenerMedico(dniMedico);
    }

    @Test
    @DisplayName("Test 02 - Verificar la existencia de un medico no existente")
    void testVerificarExistenciaMedicoNoExistente() {
        String dniMedico = "12345678A";
        ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        when(medicoClient.obtenerMedico(dniMedico)).thenReturn(responseEntity);

        assertThrows(RuntimeException.class, () -> validacionService.verificarExistenciaMedico(dniMedico));
    }

    @Test
    @DisplayName("Test 03 - Verificar la existencia de un apciente existente")
    void testVerificarExistenciaPacienteExitoso() {
        String idPaciente = "12508523L";
        ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(Map.of(), HttpStatus.OK);
        when(pacienteClient.obtenerPaciente(idPaciente)).thenReturn(responseEntity);

        validacionService.verificarExistenciaPaciente(idPaciente);

        verify(pacienteClient, times(1)).obtenerPaciente(idPaciente);
    }

    @Test
    @DisplayName("Test 04 - Verificar la existencia de un paciente no existente")
    void testVerificarExistenciaPacienteNoExistente() {
        String idPaciente = "12508523L";
        ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        when(pacienteClient.obtenerPaciente(idPaciente)).thenReturn(responseEntity);

        assertThrows(RuntimeException.class, () -> validacionService.verificarExistenciaPaciente(idPaciente));
    }

    @Test
    @DisplayName("Test 05 - Obtener cita por Id Existente")
    void testObtenerCitaPorIdExitoso() {
        String idCita = "1";
        Cita cita = new Cita();
        when(citaRepository.findById(Integer.parseInt(idCita))).thenReturn(Optional.of(cita));

        Cita result = validacionService.obtenerCitaPorId(idCita);

        verify(citaRepository, times(1)).findById(Integer.parseInt(idCita));
    }

    @Test
    @DisplayName("Test 06 - Obtener cita por Id No Existente")
    void testObtenerCitaPorIdNoExistente() {
        String idCita = "1";
        when(citaRepository.findById(Integer.parseInt(idCita))).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> validacionService.obtenerCitaPorId(idCita));
    }
}

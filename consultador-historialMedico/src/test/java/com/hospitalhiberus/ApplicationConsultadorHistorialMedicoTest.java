package com.hospitalhiberus;

import com.hospitalhiberus.controller.HistorialMedicoController;
import com.hospitalhiberus.model.HistorialMedico;
import com.hospitalhiberus.service.HistorialMedicoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HistorialMedicoController.class)
class ApplicationConsultadorHistorialMedicoTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HistorialMedicoService service;

    @Test
    @DisplayName("Test 01 - Obtener todos los historiales Medicos")
    void testObtenerTodos() throws Exception {
        HistorialMedico historialMedico = new HistorialMedico();
        historialMedico.setId("12345");
        historialMedico.setIdPaciente("12508523L");
        historialMedico.setFecha("2024-11-15");

        when(service.obtenerTodos()).thenReturn(Arrays.asList(historialMedico));

        mockMvc.perform(get("/historiales")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("12345"))
                .andExpect(jsonPath("$[0].idPaciente").value("12508523L"));
    }

    @Test
    @DisplayName("Test 02 - Obtener el historial medico por el DNI de paciente")
    void testObtenerPorId() throws Exception {
        HistorialMedico historialMedico = new HistorialMedico();
        historialMedico.setId("12345");
        historialMedico.setIdPaciente("12508523L");
        historialMedico.setFecha("2024-11-15");

        when(service.obtenerHistorialPaciente("12508523L")).thenReturn(historialMedico);

        mockMvc.perform(get("/historiales/12508523L")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("12345"))
                .andExpect(jsonPath("$.idPaciente").value("12508523L"));
    }
}

package com.hospitalhiberus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospitalhiberus.controller.HistorialMedicoController;
import com.hospitalhiberus.model.HistorialMedico;
import com.hospitalhiberus.service.HistorialMedicoService;
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


class HistorialMedicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HistorialMedicoService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testObtenerTodos() throws Exception {
        HistorialMedico historialMedico = new HistorialMedico();
        historialMedico.setIdHistorial(1);
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
    void testObtenerPorId() throws Exception {
        HistorialMedico historialMedico = new HistorialMedico();
        historialMedico.setId("12345");
        historialMedico.setIdHistorial(1);
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

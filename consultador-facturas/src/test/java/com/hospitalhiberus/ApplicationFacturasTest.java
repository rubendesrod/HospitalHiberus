package com.hospitalhiberus;


import com.hospitalhiberus.model.ESTADOS;
import com.hospitalhiberus.model.Factura;
import com.hospitalhiberus.service.FacturaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationFacturasTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacturaService service;

    @Test
    @DisplayName("Test 01 - Obtener todas las facturas")
    void testObtenerTodos() throws Exception {
        Factura factura = new Factura();
        factura.setIdFactura(1);
        factura.setEstado(ESTADOS.pendiente);
        factura.setIdMedico("12345678A");
        factura.setTotalPagar(600);
        factura.setFechaEmision(LocalDate.now());

        when(service.obtenerTodas()).thenReturn(Arrays.asList(factura));

        mockMvc.perform(get("/facturas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idMedico").value("12345678A"))
                .andExpect(jsonPath("$[0].totalPagar").value(600));
    }

    @Test
    @DisplayName("Test 02 - Obtener las facturas de un medico")
    void testObtenerFacturasDeUnMedico() throws Exception {
        Factura factura = new Factura();
        factura.setIdFactura(2);
        factura.setEstado(ESTADOS.pagado);
        factura.setIdMedico("12345678A");
        factura.setTotalPagar(730);
        factura.setFechaEmision(LocalDate.now());

        when(service.obtenerFacturasMedico("12345678A")).thenReturn(Arrays.asList(factura));

        mockMvc.perform(get("/facturas/12345678A")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idMedico").value("12345678A"))
                .andExpect(jsonPath("$[0].totalPagar").value(730));
    }

    @Test
    @DisplayName("Test 03 - Obtener las facturas de un medico segun el estado")
    void testObtenerFacturasDeUnMedicoYEstado() throws Exception {
        Factura factura = new Factura();
        factura.setIdFactura(3);
        factura.setEstado(ESTADOS.pagado);
        factura.setIdMedico("12345678A");
        factura.setTotalPagar(730);
        factura.setFechaEmision(LocalDate.now());

        Factura factura2 = new Factura();
        factura.setIdFactura(4);
        factura.setEstado(ESTADOS.pagado);
        factura.setIdMedico("12345678A");
        factura.setTotalPagar(230);
        factura.setFechaEmision(LocalDate.now());

        when(service.obtenerFacturasMedicoConEstado("12345678A", ESTADOS.pagado)).thenReturn(Arrays.asList(factura,factura2));

        mockMvc.perform(get("/facturas/12345678A/pagado")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idFactura").value(4))
                .andExpect(jsonPath("$[0].totalPagar").value(230))
                .andExpect(jsonPath("$[0].idMedico").value("12345678A"))
                .andExpect(jsonPath("$[0].estado").value("pagado"));
    }

}

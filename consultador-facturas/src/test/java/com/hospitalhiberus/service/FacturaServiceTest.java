package com.hospitalhiberus.service;

import com.hospitalhiberus.model.ESTADOS;
import com.hospitalhiberus.model.Factura;
import com.hospitalhiberus.repository.FacturaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FacturaServiceTest {

    @Mock
    private FacturaRepository facturaRepository;

    @InjectMocks
    private FacturaService facturaService;

    @Test
    @DisplayName("Test 01 - Obtener todas las facturas")
    void testObtenerTodasLasFacturas() {
        Factura factura = new Factura();
        factura.setIdFactura(1);
        factura.setEstado(ESTADOS.pendiente);
        factura.setIdMedico("12345678A");
        factura.setTotalPagar(600);

        when(facturaRepository.findAll()).thenReturn(List.of(factura));

        List<Factura> result = facturaService.obtenerTodas();

        assertEquals(1, result.size());
        assertEquals("12345678A", result.get(0).getIdMedico());
        verify(facturaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test 02 - Obtener las facturas de un medico existoso")
    void testObtenerFacturasMedicoExitoso() {
        String idMedico = "12345678A";
        Factura factura = new Factura();
        factura.setIdFactura(2);
        factura.setEstado(ESTADOS.pagado);
        factura.setIdMedico(idMedico);
        factura.setTotalPagar(730);

        when(facturaRepository.findByIdMedico(idMedico)).thenReturn(List.of(factura));

        List<Factura> result = facturaService.obtenerFacturasMedico(idMedico);

        assertEquals(1, result.size());
        assertEquals(730, result.get(0).getTotalPagar());
        verify(facturaRepository, times(1)).findByIdMedico(idMedico);
    }

    @Test
    @DisplayName("Test 03 - Obtener las facturas de un medico con estado exitoso")
    void testObtenerFacturasMedicoConEstadoExitoso() {
        String idMedico = "12345678A";
        ESTADOS estado = ESTADOS.pagado;
        Factura factura = new Factura();
        factura.setIdFactura(3);
        factura.setEstado(estado);
        factura.setIdMedico(idMedico);
        factura.setTotalPagar(500);

        when(facturaRepository.findByIdMedicoAndEstado(idMedico, estado)).thenReturn(List.of(factura));

        List<Factura> result = facturaService.obtenerFacturasMedicoConEstado(idMedico, estado);

        assertEquals(1, result.size());
        assertEquals(500, result.get(0).getTotalPagar());
        assertEquals(ESTADOS.pagado, result.get(0).getEstado());
        verify(facturaRepository, times(1)).findByIdMedicoAndEstado(idMedico, estado);
    }

    @Test
    @DisplayName("Test 05 - Obtener facturas de un medico id vacio")
    void testObtenerFacturasMedicoConIdMedicoVacio() {
        String idMedico = "";

        assertThrows(IllegalArgumentException.class, () -> facturaService.obtenerFacturasMedico(idMedico));
    }

    @Test
    @DisplayName("Test 06 - Obtener facturas de un medico con estado nulo")
    void testObtenerFacturasMedicoConEstadoNulo() {
        String idMedico = "12345678A";

        assertThrows(IllegalArgumentException.class, () -> facturaService.obtenerFacturasMedicoConEstado(idMedico, null));
    }
}

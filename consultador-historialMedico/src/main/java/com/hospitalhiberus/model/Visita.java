package com.hospitalhiberus.model;

import lombok.Data;

import java.util.List;

// Clase interna que representa una cita individual del documento historialMedico
@Data
public class Visita {
    private String fechaVisita;
    private String hora;
    private List<String> tratamiento;
    private String motivo;
}

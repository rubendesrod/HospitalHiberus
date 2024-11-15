package com.hospitalhiberus.model;

import lombok.Data;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data // Genera lo necesario para una clase getters, setter, constructures, hascode, tostring
@Document(collection = "historial_medico") // Mapeo de la coleccion
public class HistorialMedico {

    @Id
    private String id; // Mongo genera un id automaticamente
    private Integer idHistorial; // ID del historial
    private String idPaciente; // DNI del paciente que pertenece a un historial Medico
    private String fecha; // Fecha en la que se genera el historial medico (primera vez que se tiene un cita)
    private List<Visita> visitas; // lista de visitas de un paciente

}

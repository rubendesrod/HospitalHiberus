package com.hospitalhiberus.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "historial_medico")
public class HistorialMedico {

    @Id
    private String id;
    private Integer idHistorial;
    private String idPaciente;
    private String fecha;
    private List<Visita> visitas;

}

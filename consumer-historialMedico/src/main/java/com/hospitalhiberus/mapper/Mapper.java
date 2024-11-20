package com.hospitalhiberus.mapper;

import com.hospitalhiberus.model.HistorialMedico;
import com.hospitalhiberus.model.Visita;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Mapper {

    public HistorialMedico mapperHistorial(com.hospitalhiberus.avro.HistorialMedico historialAvro, List<Visita> visitas){
        HistorialMedico historial = new HistorialMedico();
        historial.setIdHistorial(historialAvro.getIdHistorial());
        historial.setIdPaciente(historialAvro.getIdPaciente());
        historial.setFecha(historialAvro.getFecha());
        historial.setVisitas(visitas);
        return historial;
    }

    public List<Visita> mapperVisitas(com.hospitalhiberus.avro.HistorialMedico historialAvro){
        return historialAvro.getVisitas().stream().map(visitaAvro -> {
            Visita v = new Visita();
            v.setFechaVisita(visitaAvro.getFechaVisita());
            v.setHora(visitaAvro.getHora());
            v.setMotivo(visitaAvro.getMotivo());
            v.setTratamiento(new ArrayList<>(visitaAvro.getTratamiento()));
            return v;
        }).collect(Collectors.toList());
    }

}

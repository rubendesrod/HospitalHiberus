package com.hospitalhiberus.mapper;

import com.hospitalhiberus.model.HistorialMedico;
import com.hospitalhiberus.model.Visita;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Mapper {

    public HistorialMedico mapperHistorial(com.hospitalhiberus.avro.HistorialMedicoValue historialAvro, List<Visita> visitas){
        HistorialMedico historial = new HistorialMedico();
        historial.setIdPaciente(historialAvro.getIdPaciente());
        historial.setFecha(historialAvro.getFecha());
        historial.setVisitas(visitas);
        return historial;
    }

    public List<Visita> mapperVisitas(com.hospitalhiberus.avro.HistorialMedicoValue historialAvro){
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

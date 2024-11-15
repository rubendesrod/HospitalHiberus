package com.hospitalhiberus.repository;

import com.hospitalhiberus.model.HistorialMedico;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface HistorialMedicoRepository extends MongoRepository<HistorialMedico, String> {

    HistorialMedico findByIdPaciente(String paciente);

}

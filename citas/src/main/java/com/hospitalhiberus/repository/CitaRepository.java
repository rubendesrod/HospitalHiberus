package com.hospitalhiberus.repository;

import com.hospitalhiberus.model.Cita;
import com.hospitalhiberus.model.ESTADOS;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Integer> {


    List<Cita> findCitaByIdPaciente(String idPaciente);

    List<Cita> findCitaByIdMedico(String idMedico);

    List<Cita> findCitaByIdMedicoAndEstado(String idMedico, ESTADOS estado);

}

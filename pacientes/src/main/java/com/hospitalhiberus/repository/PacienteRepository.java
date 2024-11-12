package com.hospitalhiberus.repository;

import com.hospitalhiberus.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, String> {

    List<Paciente> findByNombre(@Param("nombre") String nombre);
    Paciente findByDni(@Param("dni") String dni);
    boolean existsByDni(@Param("dni") String dni);
    @Transactional
    void deleteByDni(@Param("dni") String dni);

}

package com.hospitalhiberus.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "citas")
@EntityListeners(AuditingEntityListener.class)

public class Cita {

    @Id
    @Column(name = "id_cita")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_paciente")
    private String idPaciente;

    @Column(name = "id_medico")
    private String idMedico;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "hora")
    @JsonFormat(pattern = "HH:mm:ss")
    @Schema(example = "13:45:30")
    private LocalTime hora;

    @Column(name = "motivo")
    private String motivo;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private ESTADOS estado;
}

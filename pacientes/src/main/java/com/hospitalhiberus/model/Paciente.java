package com.hospitalhiberus.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "pacientes")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class Paciente {

    @Id
    @Column(name="dni")
    private String dni;

    @Column(name="nombre")
    private String nombre;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "fechanac")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(example = "2002-04-08")
    private LocalDate fechanac;

    @Column(name = "email")
    private String email;

    @Column(name = "direccion")
    private String direccion;

}

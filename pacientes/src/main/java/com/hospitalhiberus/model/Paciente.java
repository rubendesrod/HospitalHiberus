package com.hospitalhiberus.model;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pacientes")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Paciente {

    @Id
    @Column(name="dni")
    private String dni;

    @Column(name="nombre")
    private String nombre;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "fechaNac")
    private LocalDate fechaNac;

    @Column(name = "email")
    private String email;

    @Column(name = "direccion")
    private String direccion;

    @Override
    public String toString() {
        return "Paciente{" +
                "dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", fehcaNac=" + fechaNac +
                ", email='" + email + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}

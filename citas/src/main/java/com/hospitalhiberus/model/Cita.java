package com.hospitalhiberus.model;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "citas")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Cita {

    @Id
    @Column(name = "id_cita")
    private String id;

    @Column(name = "id_paciente")
    private String id_paciente;

    @Column(name = "id_medico")
    private String id_medico;

    @Column(name = "fechaHora")
    private LocalDate fechaHora;

    @Column(name = "motivo")
    private String motivo;

    @Column(name = "estado")
    private ESTADOS estado;

    @Override
    public String toString() {
        return "Cita{" +
                "id='" + id + '\'' +
                ", id_paciente='" + id_paciente + '\'' +
                ", id_medico='" + id_medico + '\'' +
                ", fechaHora=" + fechaHora +
                ", motivo='" + motivo + '\'' +
                ", estado=" + estado +
                '}';
    }
}

package co.ucentral.sistemas.proyectoCitasG15.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SEDE")
@ToString
@Builder
@Entity
public class Sede implements Serializable {

    @Id
    @Column(name = "SED_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SEDE")
    @SequenceGenerator(name = "SEQ_SEDE", sequenceName = "SEQ_SEDE", allocationSize = 1)
    private int idSede;

    @Column(name = "SED_NOMBRE", nullable = false)
    private String nombre;

    @Column(name = "SED_NUMEMPLEADO", nullable = false)
    private int numEmpleado;

    @Column(name = "SED_HORAAPERTURA", nullable = false)
    private LocalTime horaApertura;

    @Column(name = "SED_HORACIERRE", nullable = false)
    private LocalTime horaCierre;

    @Column(name = "SED_DIRECCION", nullable = false)
    private String direccion;


    public void setHoraApertura(int hora, int minuto, int segundo) {
        this.horaApertura = LocalTime.of(hora, minuto,segundo);
    }

    public void setHoraCierre(int hora, int minuto, int segundo) {
        this.horaCierre = LocalTime.of(hora, minuto, segundo);
    }
}

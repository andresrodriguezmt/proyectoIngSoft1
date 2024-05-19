package co.ucentral.sistemas.proyectocitas.entidades;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CITA")
@ToString
@Builder
@Entity
public class Cita {

    @Id
    @Column(name = "CIT_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CITA")
    @SequenceGenerator(name = "SEQ_CITA", sequenceName = "SEQ_CITA", allocationSize = 1)
    private int idCita;

    @Column(name = "CIT_NUMTURNO", nullable = false)
    private int numTurno;

    @Column(name = "CIT_ESTADO", nullable = false)
    private String estado;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "CIT_FECHA", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "CIT_HORAINICIO", nullable = true)
    private LocalTime horaInicio;

    @ManyToOne
    @JoinColumn(name = "EMP_ID")
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "SED_ID")
    private Sede sede;

    @ManyToOne
    @JoinColumn(name = "SER_ID")
    private Servicio servicio;

    @ManyToOne
    @JoinColumn(name = "CLI_ID")
    private Cliente cliente;

    public void setFecha(int anio, int mes, int dia, int hora, int minuto, int segundo) {
        this.fecha = LocalDateTime.of(anio,mes,dia, hora, minuto,segundo);
    }

    public void setHoraInicio(int hora, int minuto, int segundo) {
        this.horaInicio = LocalTime.of(hora, minuto,segundo);
    }

}

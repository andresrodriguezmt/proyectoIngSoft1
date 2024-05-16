package co.ucentral.sistemas.proyectoCitasG15.entidadesDto;

import co.ucentral.sistemas.proyectoCitasG15.entidades.Cliente;
import co.ucentral.sistemas.proyectoCitasG15.entidades.Empleado;
import co.ucentral.sistemas.proyectoCitasG15.entidades.Sede;
import co.ucentral.sistemas.proyectoCitasG15.entidades.Servicio;
import co.ucentral.sistemas.proyectoCitasG15.util.Utilidades;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CitaDto {

    private int idCita;

    private int numTurno;

    private String estado;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime fecha;

    private String fechaFormateada;

    private LocalTime horaInicio;

    private Empleado empleado;

    private Sede sede;

    private Servicio servicio;

    private Cliente cliente;


    public String getFechaFormateada() {
        return Utilidades.convertirFecha(this.fecha);
    }
}

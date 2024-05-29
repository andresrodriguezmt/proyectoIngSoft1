package co.ucentral.sistemas.proyectocitas.entidadesdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SedeDto {

    private int idSede;

    private String nombre;

    private int numEmpleado;

    private LocalDate horaApertura;

    private LocalDate horaCierre;

    private String direccion;
}


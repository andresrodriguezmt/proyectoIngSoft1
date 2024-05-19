package co.ucentral.sistemas.proyectocitas.entidadesdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SedeDto {

    private int idSede;

    private String nombre;

    private int numEmpleado;

    private Date horaApertura;

    private Date horaCierre;

    private String direccion;
}


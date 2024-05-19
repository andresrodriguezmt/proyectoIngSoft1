package co.ucentral.sistemas.proyectocitas.entidadesdto;

import co.ucentral.sistemas.proyectocitas.entidades.Sede;
import co.ucentral.sistemas.proyectocitas.entidades.Servicio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoDto {

    private int idEmpleado;
    private String nombre;
    private int edad;
    private String estado;
    private String cedula;
    private String contrasenia;
    private Sede sede;
    private Servicio servicio;
}

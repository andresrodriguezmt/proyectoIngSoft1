package co.ucentral.sistemas.proyectocitas.entidadesdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDto {

    private int idCliente;
    private String nombre;
    private int edad;
    private String cedula;
    private String estado;
    private String correo;
    private String contrasenia;

}

package co.ucentral.sistemas.proyectoCitasG15.entidadesDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDto {

    private long idCliente;

    private String nombre;

    private int edad;

    private String cedula;

    private String estado;

    private String correo;

    private String contrasenia;

}

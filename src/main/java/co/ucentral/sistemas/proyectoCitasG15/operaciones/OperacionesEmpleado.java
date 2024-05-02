package co.ucentral.sistemas.proyectoCitasG15.operaciones;

import co.ucentral.sistemas.proyectoCitasG15.entidadesDto.EmpleadoDto;

public interface OperacionesEmpleado {

    public EmpleadoDto crear(EmpleadoDto empleadoDto);

    public EmpleadoDto autenticarPorCedulayContrasenia(String cedula, String contrasenia);
}



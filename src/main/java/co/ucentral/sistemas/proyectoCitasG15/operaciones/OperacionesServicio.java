package co.ucentral.sistemas.proyectoCitasG15.operaciones;

import co.ucentral.sistemas.proyectoCitasG15.entidadesDto.ServicioDto;

import java.util.List;

public interface OperacionesServicio {
    public ServicioDto crear(ServicioDto servicioDto);

    public List<ServicioDto> buscarTodos();

    public ServicioDto buscarPorNombre(String nombre);
}



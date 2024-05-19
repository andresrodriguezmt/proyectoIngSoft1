package co.ucentral.sistemas.proyectocitas.operaciones;

import co.ucentral.sistemas.proyectocitas.entidadesdto.ServicioDto;

import java.util.List;

public interface OperacionesServicio {
    public ServicioDto crear(ServicioDto servicioDto);

    public List<ServicioDto> buscarTodos();

    public ServicioDto buscarPorNombre(String nombre);
}



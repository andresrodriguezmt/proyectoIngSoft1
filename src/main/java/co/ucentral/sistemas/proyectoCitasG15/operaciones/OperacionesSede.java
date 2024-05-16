package co.ucentral.sistemas.proyectoCitasG15.operaciones;

import co.ucentral.sistemas.proyectoCitasG15.entidades.Sede;
import co.ucentral.sistemas.proyectoCitasG15.entidadesDto.SedeDto;

import java.util.List;

public interface OperacionesSede {
    public SedeDto crear(SedeDto sedeDto);

    public List<SedeDto> buscarTodos();

    public SedeDto buscarPorNombre(String nombre);
}



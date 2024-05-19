package co.ucentral.sistemas.proyectocitas.operaciones;

import co.ucentral.sistemas.proyectocitas.entidadesdto.SedeDto;

import java.util.List;

public interface OperacionesSede {
    public SedeDto crear(SedeDto sedeDto);

    public List<SedeDto> buscarTodos();

    public SedeDto buscarPorNombre(String nombre);
}



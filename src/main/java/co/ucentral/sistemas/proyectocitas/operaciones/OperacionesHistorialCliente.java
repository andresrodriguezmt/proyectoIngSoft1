package co.ucentral.sistemas.proyectocitas.operaciones;

import co.ucentral.sistemas.proyectocitas.entidadesdto.HistorialClienteDto;

import java.util.List;

public interface OperacionesHistorialCliente {

    public HistorialClienteDto crear(HistorialClienteDto historialClienteDto);

    public List<HistorialClienteDto> buscarTodos();
}

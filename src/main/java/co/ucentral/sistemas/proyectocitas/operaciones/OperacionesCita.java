package co.ucentral.sistemas.proyectocitas.operaciones;

import co.ucentral.sistemas.proyectocitas.entidadesdto.CitaDto;
import co.ucentral.sistemas.proyectocitas.entidadesdto.ClienteDto;

import java.time.LocalDateTime;
import java.util.List;

public interface OperacionesCita {

    public CitaDto crear(CitaDto citaDto);

    public List<CitaDto> buscarTodos();

    public CitaDto buscarPorPk(int pkEntidad);

    public List<CitaDto> buscarTodosPorCliente(ClienteDto clienteDto);

    public List<CitaDto> buscarTodosPorFecha(LocalDateTime fecha);

    public Integer buscarUltimoTurnoPorServicioYSede(int idServicio, int idSede);

    public CitaDto buscarCitaPorFechaPorSedePorClienteYEstado(LocalDateTime fecha, int idSede, int idCliente, String estado);

    public List<String> buscarFechasPorServicioPorSedeYFecha(int idServicio, int idSede, LocalDateTime fecha);

    public List<CitaDto> buscarTodosPorServicioPorSedeYEstado(int idServicio, int idSede, String estado);
}

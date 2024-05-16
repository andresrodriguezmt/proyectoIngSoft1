package co.ucentral.sistemas.proyectoCitasG15.operaciones;

import co.ucentral.sistemas.proyectoCitasG15.entidades.Cita;
import co.ucentral.sistemas.proyectoCitasG15.entidadesDto.CitaDto;
import co.ucentral.sistemas.proyectoCitasG15.entidadesDto.ClienteDto;

import java.time.LocalDateTime;
import java.util.List;

public interface OperacionesCita {

    public CitaDto crear(CitaDto citaDto);

    public List<CitaDto> buscarTodos();

    public List<CitaDto> buscarTodosPorCliente(ClienteDto clienteDto);

    public List<CitaDto> buscarTodosPorFecha(LocalDateTime fecha);

    public Integer buscarUltimoTurnoPorServicioYSede(int idServicio, int idSede);
}

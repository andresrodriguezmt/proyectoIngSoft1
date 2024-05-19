package co.ucentral.sistemas.proyectocitas.servicios;

import co.ucentral.sistemas.proyectocitas.entidades.Cita;
import co.ucentral.sistemas.proyectocitas.entidades.Cliente;
import co.ucentral.sistemas.proyectocitas.entidadesdto.CitaDto;
import co.ucentral.sistemas.proyectocitas.entidadesdto.ClienteDto;
import co.ucentral.sistemas.proyectocitas.operaciones.OperacionesCita;
import co.ucentral.sistemas.proyectocitas.repositorios.RepositorioCita;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServicioCita implements OperacionesCita {


    private final ModelMapper modelMapper = new ModelMapper();

    RepositorioCita repositorioCita;

    public ServicioCita(RepositorioCita repositorioCita) {
        this.repositorioCita = repositorioCita;
    }

    @Override
    public CitaDto crear(CitaDto citaDto) {
        if(citaDto != null){
            Cita cita = repositorioCita.save(modelMapper.map(citaDto, Cita.class));
            return modelMapper.map(cita, CitaDto.class);
        }else{
            return null;
        }
    }

    @Override
    public List<CitaDto> buscarTodos() {
        TypeToken<List<CitaDto>> typeToken = new TypeToken<>(){};
        return modelMapper.map(repositorioCita.findAll(), typeToken.getType());
    }

    @Override
    public List<CitaDto> buscarTodosPorCliente(ClienteDto clienteDto) {
        TypeToken<List<CitaDto>> typeToken = new TypeToken<>(){};
        return modelMapper.map(repositorioCita.findAllByCliente(modelMapper.map(clienteDto, Cliente.class)), typeToken.getType());
    }

    @Override
    public List<CitaDto> buscarTodosPorFecha(LocalDateTime fecha) {
        TypeToken<List<CitaDto>> typeToken = new TypeToken<>(){};
        return modelMapper.map(repositorioCita.findAllByFecha(fecha), typeToken.getType());
    }

    @Override
    public Integer buscarUltimoTurnoPorServicioYSede(int idServicio, int idSede) {

        Integer numeroTurno = repositorioCita.findLastNumTurnoByServicioAndSede(idServicio, idSede);

        if(numeroTurno != null){
            return numeroTurno + 1;
        }else{
            return 1;
        }
    }

    @Override
    public CitaDto buscarCitaPorFechaPorSedePorClienteYEstado(LocalDateTime fecha, int idSede, int idCliente, String estado) {

        Cita cita = repositorioCita.findCitaByFechaBySedeByClienteAndEstado(fecha,idSede,idCliente,estado);

        if(cita != null){
            return modelMapper.map(cita, CitaDto.class);
        }else{
            return null;
        }
    }

    @Override
    public List<String> buscarFechasPorServicioPorSedeYFecha(int idServicio, int idSede, LocalDateTime fecha) {

        return repositorioCita.findFechasByServicioBySedeAndFecha(idServicio, idSede, fecha);

    }


}

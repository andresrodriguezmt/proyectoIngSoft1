package co.ucentral.sistemas.proyectoCitasG15.servicios;

import co.ucentral.sistemas.proyectoCitasG15.entidades.Cita;
import co.ucentral.sistemas.proyectoCitasG15.entidades.Cliente;
import co.ucentral.sistemas.proyectoCitasG15.entidadesDto.CitaDto;
import co.ucentral.sistemas.proyectoCitasG15.entidadesDto.ClienteDto;
import co.ucentral.sistemas.proyectoCitasG15.operaciones.OperacionesCita;
import co.ucentral.sistemas.proyectoCitasG15.repositorios.RepositorioCita;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.TypeUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServicioCita implements OperacionesCita {


    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    RepositorioCita repositorioCita;

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

}

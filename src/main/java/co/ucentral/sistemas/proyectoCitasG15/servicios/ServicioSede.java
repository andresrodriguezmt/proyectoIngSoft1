package co.ucentral.sistemas.proyectoCitasG15.servicios;

import co.ucentral.sistemas.proyectoCitasG15.entidades.Sede;
import co.ucentral.sistemas.proyectoCitasG15.entidadesDto.SedeDto;
import co.ucentral.sistemas.proyectoCitasG15.operaciones.OperacionesSede;
import co.ucentral.sistemas.proyectoCitasG15.repositorios.RepositorioSede;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class ServicioSede implements OperacionesSede {

    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    RepositorioSede repositorioSede;

    @Override
    public SedeDto crear(SedeDto sedeDto) {
        if(sedeDto != null){
            Sede sede = repositorioSede.save(modelMapper.map(sedeDto, Sede.class));
            return modelMapper.map(sede, SedeDto.class);
        }else{
            return null;
        }
    }
}


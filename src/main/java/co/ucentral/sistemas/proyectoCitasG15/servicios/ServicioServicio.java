package co.ucentral.sistemas.proyectoCitasG15.servicios;

import co.ucentral.sistemas.proyectoCitasG15.entidades.Servicio;
import co.ucentral.sistemas.proyectoCitasG15.entidadesDto.ServicioDto;
import co.ucentral.sistemas.proyectoCitasG15.operaciones.OperacionesServicio;
import co.ucentral.sistemas.proyectoCitasG15.repositorios.RepositorioServicio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioServicio implements OperacionesServicio {

    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    RepositorioServicio repositorioServicio;

    @Override
    public ServicioDto crear(ServicioDto servicioDto) {
        if(servicioDto != null){
            Servicio servicio = repositorioServicio.save(modelMapper.map(servicioDto, Servicio.class));
            return modelMapper.map(servicio, ServicioDto.class);
        }else{
            return null;
        }
    }
}

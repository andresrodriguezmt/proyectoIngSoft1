package co.ucentral.sistemas.proyectocitas.servicios;

import co.ucentral.sistemas.proyectocitas.entidades.Servicio;
import co.ucentral.sistemas.proyectocitas.entidadesdto.ServicioDto;
import co.ucentral.sistemas.proyectocitas.operaciones.OperacionesServicio;
import co.ucentral.sistemas.proyectocitas.repositorios.RepositorioServicio;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioServicio implements OperacionesServicio {

    private final ModelMapper modelMapper = new ModelMapper();

    RepositorioServicio repositorioServicio;

    public ServicioServicio(RepositorioServicio repositorioServicio) {
        this.repositorioServicio = repositorioServicio;
    }

    @Override
    public ServicioDto crear(ServicioDto servicioDto) {
        if(servicioDto != null){
            Servicio servicio = repositorioServicio.save(modelMapper.map(servicioDto, Servicio.class));
            return modelMapper.map(servicio, ServicioDto.class);
        }else{
            return null;
        }
    }

    @Override
    public List<ServicioDto> buscarTodos() {
        TypeToken<List<ServicioDto>> typeToken = new TypeToken<>(){};
        return modelMapper.map(repositorioServicio.findAll(), typeToken.getType());
    }

    @Override
    public ServicioDto buscarPorNombre(String nombre) {
        return modelMapper.map(repositorioServicio.findByNombre(nombre), ServicioDto.class);
    }
}

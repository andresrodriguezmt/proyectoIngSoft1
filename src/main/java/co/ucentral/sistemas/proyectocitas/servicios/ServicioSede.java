package co.ucentral.sistemas.proyectocitas.servicios;

import co.ucentral.sistemas.proyectocitas.entidades.Sede;
import co.ucentral.sistemas.proyectocitas.entidadesdto.SedeDto;
import co.ucentral.sistemas.proyectocitas.operaciones.OperacionesSede;
import co.ucentral.sistemas.proyectocitas.repositorios.RepositorioSede;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioSede implements OperacionesSede {

    private final ModelMapper modelMapper = new ModelMapper();

    RepositorioSede repositorioSede;

    public ServicioSede(RepositorioSede repositorioSede) {
        this.repositorioSede = repositorioSede;
    }

    @Override
    public SedeDto crear(SedeDto sedeDto) {
        if(sedeDto != null){
            Sede sede = repositorioSede.save(modelMapper.map(sedeDto, Sede.class));
            return modelMapper.map(sede, SedeDto.class);
        }else{
            return null;
        }
    }

    @Override
    public List<SedeDto> buscarTodos() {
        TypeToken<List<SedeDto>> typeToken = new TypeToken<>() {};
        return modelMapper.map(repositorioSede.findAll(), typeToken.getType());
    }

    @Override
    public SedeDto buscarPorNombre(String nombre) {
        return modelMapper.map(repositorioSede.findByNombre(nombre), SedeDto.class);
    }

    @Override
    public SedeDto buscarPorPk(int pkEntidad) {
        return modelMapper.map(repositorioSede.findById(pkEntidad).orElse(null), SedeDto.class);
    }
}


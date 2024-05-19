package co.ucentral.sistemas.proyectocitas.servicios;

import co.ucentral.sistemas.proyectocitas.entidades.Empleado;
import co.ucentral.sistemas.proyectocitas.entidadesdto.EmpleadoDto;
import co.ucentral.sistemas.proyectocitas.operaciones.OperacionesEmpleado;
import co.ucentral.sistemas.proyectocitas.repositorios.RepositorioEmpleado;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ServicioEmpleado implements OperacionesEmpleado {

    private final ModelMapper modelMapper = new ModelMapper();


    RepositorioEmpleado repositorioEmpleado;

    public ServicioEmpleado(RepositorioEmpleado repositorioEmpleado) {
        this.repositorioEmpleado = repositorioEmpleado;
    }

    @Override
    public EmpleadoDto crear(EmpleadoDto empleadoDto) {
        if(empleadoDto != null){
            Empleado empleado = repositorioEmpleado.save(modelMapper.map(empleadoDto, Empleado.class));
            return modelMapper.map(empleado, EmpleadoDto.class);
        }else{
            return null;
        }
    }

    @Override
    public EmpleadoDto autenticarPorCedulayContrasenia(String cedula, String contrasenia) {

        Empleado empleado = repositorioEmpleado.buscarEmpPorCedulayContrasenia(cedula,contrasenia);

        if(empleado != null){
            return modelMapper.map(empleado, EmpleadoDto.class);
        }else{
            return null;
        }
    }

    @Override
    public EmpleadoDto buscarPorPk(int pk) {
        return modelMapper.map(repositorioEmpleado.findById(pk).orElse(null), EmpleadoDto.class);
    }
}


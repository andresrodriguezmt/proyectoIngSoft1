package co.ucentral.sistemas.proyectoCitasG15.servicios;

import co.ucentral.sistemas.proyectoCitasG15.entidades.Empleado;
import co.ucentral.sistemas.proyectoCitasG15.entidadesDto.EmpleadoDto;
import co.ucentral.sistemas.proyectoCitasG15.operaciones.OperacionesEmpleado;
import co.ucentral.sistemas.proyectoCitasG15.repositorios.RepositorioEmpleado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioEmpleado implements OperacionesEmpleado {

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    RepositorioEmpleado repositorioEmpleado;

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
}


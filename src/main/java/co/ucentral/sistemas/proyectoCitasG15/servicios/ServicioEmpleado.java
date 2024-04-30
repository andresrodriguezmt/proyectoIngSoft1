package co.ucentral.sistemas.proyectoCitasG15.servicios;

import co.ucentral.sistemas.proyectoCitasG15.entidades.Empleado;
import co.ucentral.sistemas.proyectoCitasG15.entidadesDto.EmpleadoDto;
import co.ucentral.sistemas.proyectoCitasG15.operaciones.OperacionesEmpleado;
import co.ucentral.sistemas.proyectoCitasG15.repositorios.RepositorioEmpleado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class ServicioEmpleado implements OperacionesEmpleado {

    private ModelMapper modelMapper = new ModelMapper();

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
}


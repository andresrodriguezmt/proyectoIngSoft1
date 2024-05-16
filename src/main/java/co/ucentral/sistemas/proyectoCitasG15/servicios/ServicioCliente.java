package co.ucentral.sistemas.proyectoCitasG15.servicios;

import co.ucentral.sistemas.proyectoCitasG15.entidades.Cliente;
import co.ucentral.sistemas.proyectoCitasG15.entidadesDto.ClienteDto;
import co.ucentral.sistemas.proyectoCitasG15.operaciones.OperacionesCliente;
import co.ucentral.sistemas.proyectoCitasG15.repositorios.RepositorioCliente;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioCliente implements OperacionesCliente {

    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    RepositorioCliente repositorioCliente;

    @Override
    public ClienteDto crear(ClienteDto clienteDto) {
        if(clienteDto != null){
            Cliente cliente = repositorioCliente.save(modelMapper.map(clienteDto, Cliente.class));
            return modelMapper.map(cliente, ClienteDto.class);

        }else{
            return null;
        }
    }

    @Override
    public ClienteDto autenticarPorCedyContra(String cedula, String contrasenia) {

        Cliente cliente = repositorioCliente.buscarCliePorCedulayContrasenia(cedula, contrasenia);

        if(cliente != null){
            return modelMapper.map(cliente, ClienteDto.class);
        }else {
            return null;
        }
    }

    @Override
    public ClienteDto autenticarPorCorreoyContra(String correo, String contrasenia) {

        Cliente cliente = repositorioCliente.buscarCliePorCorreoyContrasenia(correo, contrasenia);

        if(cliente != null){
            return modelMapper.map(cliente, ClienteDto.class);
        }else {
            return null;
        }
    }

    @Override
    public ClienteDto buscarPorPk(int pkEntidad) {
        return modelMapper.map(repositorioCliente.findById(pkEntidad).orElse(null), ClienteDto.class);
    }

}

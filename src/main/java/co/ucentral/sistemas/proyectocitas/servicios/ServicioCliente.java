package co.ucentral.sistemas.proyectocitas.servicios;

import co.ucentral.sistemas.proyectocitas.entidades.Cliente;
import co.ucentral.sistemas.proyectocitas.entidadesdto.ClienteDto;
import co.ucentral.sistemas.proyectocitas.operaciones.OperacionesCliente;
import co.ucentral.sistemas.proyectocitas.repositorios.RepositorioCliente;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ServicioCliente implements OperacionesCliente {

    private final ModelMapper modelMapper = new ModelMapper();

    RepositorioCliente repositorioCliente;

    public ServicioCliente(RepositorioCliente repositorioCliente) {
        this.repositorioCliente = repositorioCliente;
    }

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

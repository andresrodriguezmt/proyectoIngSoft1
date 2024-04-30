package co.ucentral.sistemas.proyectoCitasG15.servicios;

import co.ucentral.sistemas.proyectoCitasG15.entidades.Cliente;
import co.ucentral.sistemas.proyectoCitasG15.entidadesDto.ClienteDto;
import co.ucentral.sistemas.proyectoCitasG15.operaciones.OperacionesCliente;
import co.ucentral.sistemas.proyectoCitasG15.repositorios.RepositorioCliente;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

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
}

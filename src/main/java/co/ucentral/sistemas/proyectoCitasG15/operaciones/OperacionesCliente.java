package co.ucentral.sistemas.proyectoCitasG15.operaciones;

import co.ucentral.sistemas.proyectoCitasG15.entidadesDto.ClienteDto;

public interface OperacionesCliente {

    public ClienteDto crear(ClienteDto clienteDto);

    public ClienteDto autenticarPorCedyContra(String cedula, String contrasenia);

    public ClienteDto autenticarPorCorreoyContra(String correo, String contrasenia);

    public ClienteDto buscarPorPk(int pkEntidad);

}



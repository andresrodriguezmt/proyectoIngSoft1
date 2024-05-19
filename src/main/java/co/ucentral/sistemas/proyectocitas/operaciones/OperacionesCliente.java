package co.ucentral.sistemas.proyectocitas.operaciones;

import co.ucentral.sistemas.proyectocitas.entidadesdto.ClienteDto;

public interface OperacionesCliente {

    public ClienteDto crear(ClienteDto clienteDto);

    public ClienteDto autenticarPorCedyContra(String cedula, String contrasenia);

    public ClienteDto autenticarPorCorreoyContra(String correo, String contrasenia);

    public ClienteDto buscarPorPk(int pkEntidad);

}



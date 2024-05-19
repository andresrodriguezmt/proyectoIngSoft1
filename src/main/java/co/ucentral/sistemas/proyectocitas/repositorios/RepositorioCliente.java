package co.ucentral.sistemas.proyectocitas.repositorios;

import co.ucentral.sistemas.proyectocitas.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RepositorioCliente extends JpaRepository<Cliente, Integer> {

    @Query(value = "SELECT * FROM CLIENTE where cli_correo = ?1 and cli_contrasenia = ?2 ", nativeQuery = true)
    Cliente buscarCliePorCorreoyContrasenia(String correo ,String contrasenia);

    @Query(value = "SELECT * FROM CLIENTE where cli_cedula = ?1 and cli_contrasenia = ?2 ", nativeQuery = true)
    Cliente buscarCliePorCedulayContrasenia(String cedula ,String contrasenia);

}

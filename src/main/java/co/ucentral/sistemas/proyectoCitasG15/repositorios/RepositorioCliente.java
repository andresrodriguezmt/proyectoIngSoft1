package co.ucentral.sistemas.proyectoCitasG15.repositorios;

import co.ucentral.sistemas.proyectoCitasG15.entidades.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface RepositorioCliente extends CrudRepository<Cliente, Long> {
}

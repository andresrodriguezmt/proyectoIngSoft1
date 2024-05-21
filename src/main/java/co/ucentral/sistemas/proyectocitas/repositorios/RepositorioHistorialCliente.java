package co.ucentral.sistemas.proyectocitas.repositorios;

import co.ucentral.sistemas.proyectocitas.entidades.HistorialCliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioHistorialCliente extends JpaRepository<HistorialCliente, Integer> {
}

package co.ucentral.sistemas.proyectocitas.repositorios;

import co.ucentral.sistemas.proyectocitas.entidades.Sede;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioSede extends JpaRepository<Sede, Integer> {
    Sede findByNombre(String nombre);
}

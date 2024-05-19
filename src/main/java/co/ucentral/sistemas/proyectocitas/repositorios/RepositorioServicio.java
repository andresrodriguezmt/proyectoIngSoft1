package co.ucentral.sistemas.proyectocitas.repositorios;

import co.ucentral.sistemas.proyectocitas.entidades.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioServicio extends JpaRepository<Servicio, Integer> {
    Servicio findByNombre(String nombre);
}


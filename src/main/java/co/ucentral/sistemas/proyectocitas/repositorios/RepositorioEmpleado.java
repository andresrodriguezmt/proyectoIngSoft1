package co.ucentral.sistemas.proyectocitas.repositorios;

import co.ucentral.sistemas.proyectocitas.entidades.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RepositorioEmpleado extends JpaRepository<Empleado, Integer> {

    @Query(value = "SELECT * FROM EMPLEADO where emp_cedula = ?1 and emp_contrasenia = ?2", nativeQuery = true)
    Empleado buscarEmpPorCedulayContrasenia(String cedula, String contrasenia);
}



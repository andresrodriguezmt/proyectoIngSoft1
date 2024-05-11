package co.ucentral.sistemas.proyectoCitasG15.repositorios;

import co.ucentral.sistemas.proyectoCitasG15.entidades.Empleado;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RepositorioEmpleado extends CrudRepository<Empleado, Long> {

    @Query(value = "SELECT * FROM EMPLEADO where emp_cedula = ?1 and emp_contrasenia = ?2", nativeQuery = true)
    Empleado buscarEmpPorCedulayContrasenia(String cedula, String contrasenia);
}



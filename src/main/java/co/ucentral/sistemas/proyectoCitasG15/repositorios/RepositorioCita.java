package co.ucentral.sistemas.proyectoCitasG15.repositorios;

import co.ucentral.sistemas.proyectoCitasG15.entidades.Cita;
import co.ucentral.sistemas.proyectoCitasG15.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RepositorioCita extends JpaRepository<Cita, Integer> {

    public List<Cita> findAllByCliente(Cliente cliente);

    public List<Cita> findAllByFecha(LocalDateTime fecha);

    @Query(value = "SELECT cit_numturno FROM cita where sed_id = ?1 and ser_id = ?2 order by cit_numturno desc LIMIT 1;", nativeQuery = true)
    public Integer findLastNumTurnoByServicioAndSede(int idServicio, int idSede);
}

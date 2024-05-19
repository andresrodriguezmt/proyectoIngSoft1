package co.ucentral.sistemas.proyectocitas.repositorios;

import co.ucentral.sistemas.proyectocitas.entidades.Cita;
import co.ucentral.sistemas.proyectocitas.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface RepositorioCita extends JpaRepository<Cita, Integer> {

    public List<Cita> findAllByCliente(Cliente cliente);

    public List<Cita> findAllByFecha(LocalDateTime fecha);

    @Query(value = "SELECT cit_numturno FROM cita where sed_id = ?1 and ser_id = ?2 order by cit_numturno desc LIMIT 1;", nativeQuery = true)
    public Integer findLastNumTurnoByServicioAndSede(int idServicio, int idSede);

    @Query(value = "SELECT * FROM cita WHERE cit_fecha = :fecha AND sed_id = :idSede AND cli_id = :idCliente AND cit_estado = :estado", nativeQuery = true)
    public Cita findCitaByFechaBySedeByClienteAndEstado(@Param("fecha") LocalDateTime fecha, @Param("idSede") int idSede, @Param("idCliente") int idCliente, @Param("estado") String estado);

    @Query(value = "SELECT cit_fecha FROM cita WHERE ser_id = :idServicio AND sed_id = :idSede AND cit_fecha = :fecha", nativeQuery = true)
    public List<String> findFechasByServicioBySedeAndFecha(@Param("idServicio") int idServicio, @Param("idSede") int idSede, @Param("fecha") LocalDateTime fecha);

    @Query(value = "SELECT * FROM cita WHERE ser_id = :idServicio AND sed_id = :idSede AND cit_estado = :estado", nativeQuery = true)
    public List<Cita> findAllByServicioAndSede(@Param("idServicio") int idServicio, @Param("idSede") int idSede, @Param("estado") String estado);
}

package co.ucentral.sistemas.proyectocitas.repositorios;

import co.ucentral.sistemas.proyectocitas.entidades.HistorialCliente;
import co.ucentral.sistemas.proyectocitas.entidadesdto.PrimerReporteDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RepositorioHistorialCliente extends JpaRepository<HistorialCliente, Integer> {

    @Query(value = "select SED.sed_nombre as SedeNombre, SER.ser_nombre as ServicioNombre, count(*) as NumeroClientesAtendidos from historialCliente as HC, cita as CI, servicio as SER, sede as SED where HC.cit_id = CI.cit_id and CI.ser_id = SER.ser_id and CI.sed_id = SED.sed_id and SED.sed_id = :idSede and CI.cit_estado = 'Terminado' group by SedeNombre, ServicioNombre order by NumeroClientesAtendidos desc limit 1;", nativeQuery = true)
    public PrimerReporteDto reporteServicioMasUsado(@Param("idSede") long idSede);
}

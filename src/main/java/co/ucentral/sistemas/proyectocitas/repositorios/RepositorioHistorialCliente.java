package co.ucentral.sistemas.proyectocitas.repositorios;

import co.ucentral.sistemas.proyectocitas.entidades.HistorialCliente;
import co.ucentral.sistemas.proyectocitas.entidadesdto.PrimerReporteDto;
import co.ucentral.sistemas.proyectocitas.entidadesdto.SegundoReporteDto;
import co.ucentral.sistemas.proyectocitas.entidadesdto.TercerCuartoReporteDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RepositorioHistorialCliente extends JpaRepository<HistorialCliente, Integer> {

    @Query(value = "select SED.sed_nombre as SedeNombre, SER.ser_nombre as ServicioNombre, count(*) as NumeroClientesAtendidos from historialCliente as HC, cita as CI, servicio as SER, sede as SED where HC.cit_id = CI.cit_id and CI.ser_id = SER.ser_id and CI.sed_id = SED.sed_id and SED.sed_id = :idSede and CI.cit_estado = 'Terminado' group by SedeNombre, ServicioNombre order by NumeroClientesAtendidos desc limit 1;", nativeQuery = true)
    public PrimerReporteDto reporteServicioMasUsado(@Param("idSede") long idSede);

    @Query(value = "select SER.ser_nombre as ServicioNombre, EXTRACT(HOUR FROM SUM(CI.cit_duracion) / :numeroCitas) || ' horas ' || EXTRACT(MINUTE FROM SUM(CI.cit_duracion) / :numeroCitas) || ' minutos ' || TO_CHAR(EXTRACT(SECOND FROM SUM(CI.cit_duracion) / :numeroCitas), 'FM00') || ' segundos' AS PromedioAtencion from historialCliente as HC, cita as CI, servicio as SER, sede as SED where HC.cit_id = CI.cit_id and CI.sed_id = SED.sed_id and CI.ser_id = SER.ser_id and SED.sed_id = :idSede and SER.ser_id = :idServicio group by ServicioNombre;", nativeQuery = true)
    public SegundoReporteDto reporteTiempoPromedioAtencion(@Param("numeroCitas") long numeroCitas, @Param("idSede") long idSede, @Param("idServicio") long idServicio);

    @Query(value = "select EMP.emp_nombre as NombreEmpleado, SED.sed_nombre as NombreSede, SER.ser_nombre as NombreServicio, count(*) as CantidadAtencion from historialCliente as HC, cita as CI, servicio as SER, sede as SED, empleado as EMP where HC.cit_id = CI.cit_id and CI.ser_id = SER.ser_id and CI.sed_id = SED.sed_id and CI.emp_id = EMP.emp_id group by NombreEmpleado, NombreSede, NombreServicio order by CantidadAtencion desc limit 1;", nativeQuery = true)
    public TercerCuartoReporteDto reporteEmpleadoClientesMasAtendidos();

}

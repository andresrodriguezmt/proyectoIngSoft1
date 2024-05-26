package co.ucentral.sistemas.proyectocitas.servicios;

import co.ucentral.sistemas.proyectocitas.entidades.HistorialCliente;
import co.ucentral.sistemas.proyectocitas.entidadesdto.*;
import co.ucentral.sistemas.proyectocitas.operaciones.OperacionesHistorialCliente;
import co.ucentral.sistemas.proyectocitas.repositorios.RepositorioHistorialCliente;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ServicioHistorialCliente implements OperacionesHistorialCliente {

    private final ModelMapper modelMapper = new ModelMapper();

    RepositorioHistorialCliente repositorioHistorialCliente;

    public ServicioHistorialCliente(RepositorioHistorialCliente repositorioHistorialCliente) {
        this.repositorioHistorialCliente = repositorioHistorialCliente;
    }

    @Override
    public HistorialClienteDto crear(HistorialClienteDto historialClienteDto) {

        if (historialClienteDto != null) {
            HistorialCliente historialCliente = repositorioHistorialCliente.save(modelMapper.map(historialClienteDto, HistorialCliente.class));

            return modelMapper.map(historialCliente, HistorialClienteDto.class);
        } else {
            return null;
        }
    }
    @Override
    public List<HistorialClienteDto> buscarTodos() {

        TypeToken<List<HistorialClienteDto>> typeToken = new TypeToken<>(){};

        return modelMapper.map(repositorioHistorialCliente.findAll(), typeToken.getType());
    }

    @Override
    public ReporteServicioMasUsadoDto reporteServicioMasUsado(long idSede) {

        PrimerReporteDto primerReporteDto = repositorioHistorialCliente.reporteServicioMasUsado(idSede);

        if(primerReporteDto != null){

            return  ReporteServicioMasUsadoDto
                    .builder()
                    .nombreSede(primerReporteDto.getSedeNombre())
                    .nombreServicio(primerReporteDto.getServicioNombre())
                    .cantidadAtendidos(primerReporteDto.getNumeroClientesAtendidos())
                    .build();
        }else {
            return  null;
        }
    }

    @Override
    public ReporteTiempoPromedioAtencionDto reporteTiempoPromedioAtencion(int numeroCitas, int idSede, int idServicio) {

        SegundoReporteDto segundoReporteDto = repositorioHistorialCliente.reporteTiempoPromedioAtencion(numeroCitas,idSede, idServicio);

        if(segundoReporteDto != null){
            return ReporteTiempoPromedioAtencionDto
                    .builder()
                    .servicio(segundoReporteDto.getServicioNombre())
                    .tiempoPromedio(segundoReporteDto.getPromedioAtencion())
                    .build();

        }else {
            return null;
        }

    }
}

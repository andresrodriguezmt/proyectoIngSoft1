package co.ucentral.sistemas.proyectocitas.controladores;

import co.ucentral.sistemas.proyectocitas.entidades.Cita;
import co.ucentral.sistemas.proyectocitas.entidadesdto.*;
import co.ucentral.sistemas.proyectocitas.servicios.*;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


@Controller
@Log4j2
public class ControladorHistorialCliente {

    private final ModelMapper modelMapper = new ModelMapper();

    ServicioHistorialCliente servicioHistorialCliente;

    ServicioCita servicioCita;

    ServicioEmpleado servicioEmpleado;

    ServicioCliente servicioCliente;

    ServicioSede servicioSede;

    ServicioServicio servicioServicio;

    String estadoTerminado = "Terminado";
    String estadoLibre = "Libre";
    String nombreIdEmpleado = "idEmpleado";

    public ControladorHistorialCliente(ServicioHistorialCliente servicioHistorialCliente, ServicioCita servicioCita, ServicioEmpleado servicioEmpleado, ServicioCliente servicioCliente, ServicioSede servicioSede, ServicioServicio servicioServicio) {
        this.servicioHistorialCliente = servicioHistorialCliente;
        this.servicioCita = servicioCita;
        this.servicioEmpleado = servicioEmpleado;
        this.servicioCliente = servicioCliente;
        this.servicioSede = servicioSede;
        this.servicioServicio = servicioServicio;
    }

    @GetMapping({"/cerrar/cita/{idCita}"})
    public String formularioTerminarCita(@PathVariable int idCita,  Model model){

        HistorialClienteDto historialClienteDto = new HistorialClienteDto();

        CitaDto citaDto = servicioCita.buscarPorPk(idCita);
        citaDto.setEstado(estadoTerminado);

        String fecha = String.format("%04d-%02d-%02d", citaDto.getFecha().getYear(),citaDto.getFecha().getMonthValue(), citaDto.getFecha().getDayOfMonth());
        String tiempo = String.format("%02d:%02d", citaDto.getFecha().getHour(), citaDto.getFecha().getMinute());

        model.addAttribute("cita", citaDto);
        model.addAttribute("fecha",fecha + " " + tiempo);
        model.addAttribute("historialCliente", historialClienteDto);

        return "formularioTerminarCita";

    }

    @PostMapping({"/terminar/cita/{idCita}"})
    public String terminarCita(@PathVariable int idCita, @ModelAttribute("historialCliente") HistorialClienteDto historialClienteDto, RedirectAttributes redirectAttributes){

        LocalTime horaFin = LocalTime.now();
        CitaDto citaDto = servicioCita.buscarPorPk(idCita);

        long segundosDiferencia = ChronoUnit.SECONDS.between(citaDto.getHoraInicio(),horaFin);

        // Si la hora de llegada es menor que la hora de salida, ajusta la diferencia
        if (horaFin.isBefore(citaDto.getHoraInicio())) {
            segundosDiferencia += 24 * 60 * 60; // Suma 24 horas en segundos
        }

        citaDto.setEstado(estadoTerminado);
        citaDto.setDuracion(LocalTime.ofSecondOfDay(segundosDiferencia));

        servicioCita.modificar(citaDto);

        historialClienteDto.setCita(modelMapper.map(citaDto, Cita.class));
        servicioHistorialCliente.crear(historialClienteDto);

        citaDto.getEmpleado().setEstado(estadoLibre);
        servicioEmpleado.modificar(modelMapper.map(citaDto.getEmpleado(), EmpleadoDto.class));

        citaDto.getCliente().setEstado(estadoLibre);
        servicioCliente.modificar(modelMapper.map(citaDto.getCliente(), ClienteDto.class));

        redirectAttributes.addAttribute(nombreIdEmpleado, citaDto.getEmpleado().getIdEmpleado());
        return "redirect:/principal/empleado/{idEmpleado}";
    }

    @GetMapping({"/cerrado/automatico/cita/{idCita}"})
    public String cerradoAutomatico(@PathVariable int idCita, RedirectAttributes redirectAttributes){

        HistorialClienteDto historialClienteDto = new HistorialClienteDto();
        CitaDto citaDto = servicioCita.buscarPorPk(idCita);

        citaDto.setEstado(estadoTerminado);
        citaDto.setHoraInicio(LocalTime.now());
        citaDto.setDuracion(LocalTime.of(0,0,0));
        servicioCita.modificar(citaDto);

        historialClienteDto.setCita(modelMapper.map(citaDto, Cita.class));
        historialClienteDto.setObservaciones("Terminada la cita por inasistencia por parte del cliente");
        servicioHistorialCliente.crear(historialClienteDto);

        citaDto.getCliente().setEstado(estadoLibre);
        citaDto.getEmpleado().setEstado(estadoLibre);

        servicioCliente.modificar(modelMapper.map(citaDto.getCliente(), ClienteDto.class));
        servicioEmpleado.modificar(modelMapper.map(citaDto.getEmpleado(), EmpleadoDto.class));

        redirectAttributes.addAttribute(nombreIdEmpleado, citaDto.getEmpleado().getIdEmpleado());
        return "redirect:/principal/empleado/{idEmpleado}";
    }

    @GetMapping({"/reportes/{idEmpleado}"})
    public String visualizarPaginaReportes(@PathVariable int idEmpleado, Model model){

        List<ReporteServicioMasUsadoDto> listaReporteServicioMasUsado = reporteServicioMasUsado();

        model.addAttribute(nombreIdEmpleado, idEmpleado);
        model.addAttribute("listaReporteServicioMasUsado", listaReporteServicioMasUsado);

        model.addAttribute("listaReporteTiempoPromedio1", reporteTiempoPromedioAtencion(1));
        model.addAttribute("listaReporteTiempoPromedio2", reporteTiempoPromedioAtencion(2));
        model.addAttribute("listaReporteTiempoPromedio3", reporteTiempoPromedioAtencion(3));
        model.addAttribute("listaReporteTiempoPromedio4", reporteTiempoPromedioAtencion(4));

        model.addAttribute("empleadoMasAtencion", servicioHistorialCliente.reporteEmpleadoMasClienteAtendido());
        return "reportes";
    }

    public List<ReporteServicioMasUsadoDto> reporteServicioMasUsado(){

        ReporteServicioMasUsadoDto reporteServicio;

        List<ReporteServicioMasUsadoDto> listaReportes = new ArrayList<>();

        for(int i = 1; i < 5; i++){

            reporteServicio = servicioHistorialCliente.reporteServicioMasUsado(i);

            if(reporteServicio == null){
                SedeDto sedeDto = servicioSede.buscarPorPk(i);

                reporteServicio = ReporteServicioMasUsadoDto
                        .builder()
                        .nombreSede(sedeDto.getNombre())
                        .nombreServicio("La sede no tiene un historial de citas")
                        .cantidadAtendidos(0L)
                        .build();
            }

            listaReportes.add(reporteServicio);
        }

        return listaReportes;
    }

    public List<ReporteTiempoPromedioAtencionDto> reporteTiempoPromedioAtencion(int idSede){

        ReporteTiempoPromedioAtencionDto reporteTiempoPromedioAtencionDto;
        List<ReporteTiempoPromedioAtencionDto> listaTiempoAtencion = new ArrayList<>();

        for(int idServicio = 1; idServicio < 4; idServicio++){

            List<CitaDto> listaCitas = servicioCita.buscarTodosPorServicioPorSedeYEstado(idServicio,idSede, estadoTerminado);

            if(listaCitas.isEmpty()){
                ServicioDto servicioDto = servicioServicio.buscarPorPk(idServicio);

                reporteTiempoPromedioAtencionDto = ReporteTiempoPromedioAtencionDto
                        .builder()
                        .servicio(servicioDto.getNombre())
                        .tiempoPromedio("0 horas 0 minutos 0 segundos")
                        .build();

            }else{
                reporteTiempoPromedioAtencionDto = servicioHistorialCliente.reporteTiempoPromedioAtencion(listaCitas.size(), idSede, idServicio);

            }

            listaTiempoAtencion.add(reporteTiempoPromedioAtencionDto);
        }

        return listaTiempoAtencion;
    }

}

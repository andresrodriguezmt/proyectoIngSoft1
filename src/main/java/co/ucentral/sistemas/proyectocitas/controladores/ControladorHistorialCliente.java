package co.ucentral.sistemas.proyectocitas.controladores;

import co.ucentral.sistemas.proyectocitas.entidades.Cita;
import co.ucentral.sistemas.proyectocitas.entidades.Empleado;
import co.ucentral.sistemas.proyectocitas.entidadesdto.CitaDto;
import co.ucentral.sistemas.proyectocitas.entidadesdto.ClienteDto;
import co.ucentral.sistemas.proyectocitas.entidadesdto.EmpleadoDto;
import co.ucentral.sistemas.proyectocitas.entidadesdto.HistorialClienteDto;
import co.ucentral.sistemas.proyectocitas.servicios.ServicioCita;
import co.ucentral.sistemas.proyectocitas.servicios.ServicioCliente;
import co.ucentral.sistemas.proyectocitas.servicios.ServicioEmpleado;
import co.ucentral.sistemas.proyectocitas.servicios.ServicioHistorialCliente;
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


@Controller
@Log4j2
public class ControladorHistorialCliente {

    private final ModelMapper modelMapper = new ModelMapper();

    ServicioHistorialCliente servicioHistorialCliente;

    ServicioCita servicioCita;

    ServicioEmpleado servicioEmpleado;

    ServicioCliente servicioCliente;

    String estadoTerminado = "Terminado";
    String estadoLibre = "Libre";

    public ControladorHistorialCliente(ServicioHistorialCliente servicioHistorialCliente, ServicioCita servicioCita, ServicioEmpleado servicioEmpleado, ServicioCliente servicioCliente) {
        this.servicioHistorialCliente = servicioHistorialCliente;
        this.servicioCita = servicioCita;
        this.servicioEmpleado = servicioEmpleado;
        this.servicioCliente = servicioCliente;
    }

    @GetMapping({"/cerrar/cita/{idCita}"})
    public String formularioTerminarCita(@PathVariable int idCita,  Model model){

        HistorialClienteDto historialClienteDto = new HistorialClienteDto();

        CitaDto citaDto = servicioCita.buscarPorPk(idCita);
        citaDto.setEstado(estadoTerminado);

        String fecha = String.format("%04d-%02d-%02d", citaDto.getFecha().getYear(),citaDto.getFecha().getMonthValue(), citaDto.getFecha().getDayOfMonth());
        String tiempo = String.format("%02d:%02d", citaDto.getFecha().getHour(), citaDto.getFecha().getMinute());

        model.addAttribute("pkEmpleado", citaDto.getEmpleado().getIdEmpleado());
        model.addAttribute("cita", citaDto);
        model.addAttribute("fecha",fecha + " " + tiempo);
        model.addAttribute("historialCliente", historialClienteDto);

        return "formularioTerminarCita";

    }

    @PostMapping({"/terminar/cita/{idCita}"})
    public String terminarCita(@PathVariable int idCita, @ModelAttribute("historialCliente") HistorialClienteDto historialClienteDto, RedirectAttributes redirectAttributes){

        EmpleadoDto empleadoDto = servicioEmpleado.buscarPorPk(Integer.parseInt(historialClienteDto.getCita().getEmpleado().getNombre()));


        CitaDto citaDto = servicioCita.buscarPorPk(idCita);
        citaDto.setEstado(estadoTerminado);
        citaDto.setEmpleado(modelMapper.map(empleadoDto, Empleado.class));

        historialClienteDto.setCita(modelMapper.map(citaDto, Cita.class));
        servicioHistorialCliente.crear(historialClienteDto);

        empleadoDto.setEstado(estadoLibre);
        servicioEmpleado.modificar(empleadoDto);

        servicioCita.modificar(citaDto);

        redirectAttributes.addAttribute("idEmpleado", citaDto.getEmpleado().getIdEmpleado());
        return "redirect:/principal/empleado/{idEmpleado}";
    }

    @GetMapping({"/cerrado/automatico/cita/{idCita}"})
    public String cerradoAutomatico(@PathVariable int idCita, RedirectAttributes redirectAttributes){

        HistorialClienteDto historialClienteDto = new HistorialClienteDto();
        CitaDto citaDto = servicioCita.buscarPorPk(idCita);

        citaDto.setEstado(estadoTerminado);
        citaDto.setHoraInicio(LocalTime.now());
        servicioCita.modificar(citaDto);

        historialClienteDto.setCita(modelMapper.map(citaDto, Cita.class));
        historialClienteDto.setObservaciones("Terminada la cita por inasistencia por parte del cliente");
        servicioHistorialCliente.crear(historialClienteDto);

        citaDto.getCliente().setEstado(estadoLibre);
        citaDto.getEmpleado().setEstado(estadoLibre);

        servicioCliente.modificar(modelMapper.map(citaDto.getCliente(), ClienteDto.class));
        servicioEmpleado.modificar(modelMapper.map(citaDto.getEmpleado(), EmpleadoDto.class));

        redirectAttributes.addAttribute("idEmpleado", citaDto.getEmpleado().getIdEmpleado());
        return "redirect:/principal/empleado/{idEmpleado}";
    }
}

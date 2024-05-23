package co.ucentral.sistemas.proyectocitas.controladores;

import co.ucentral.sistemas.proyectocitas.entidades.Cliente;
import co.ucentral.sistemas.proyectocitas.entidades.Empleado;
import co.ucentral.sistemas.proyectocitas.entidades.Sede;
import co.ucentral.sistemas.proyectocitas.entidades.Servicio;
import co.ucentral.sistemas.proyectocitas.entidadesdto.CitaDto;
import co.ucentral.sistemas.proyectocitas.entidadesdto.ClienteDto;
import co.ucentral.sistemas.proyectocitas.entidadesdto.EmpleadoDto;
import co.ucentral.sistemas.proyectocitas.servicios.*;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;

/**
 * author Andres
 */
@Controller
@Log4j2
public class ControladorCita {

    private final ModelMapper modelMapper = new ModelMapper();

    ServicioCita servicioCita;
    ServicioSede servicioSede;
    ServicioServicio servicioServicio;
    ServicioCliente servicioCliente;
    ServicioEmpleado servicioEmpleado;
    String estadoActivo = "Activo";
    String estadoTerminado = "Terminado";
    String estadoEspera = "En espera";

    public ControladorCita(ServicioCita servicioCita, ServicioSede servicioSede, ServicioServicio servicioServicio, ServicioCliente servicioCliente, ServicioEmpleado servicioEmpleado) {
        this.servicioCita = servicioCita;
        this.servicioSede = servicioSede;
        this.servicioServicio = servicioServicio;
        this.servicioCliente = servicioCliente;
        this.servicioEmpleado = servicioEmpleado;
    }

    @GetMapping("/principal/cliente/{codigo}")
    public String listarCitas(@PathVariable int codigo, Model model){

        ClienteDto clienteDto = servicioCliente.buscarPorPk(codigo);

        model.addAttribute("listaCitasAsistencia", servicioCita.buscarTodosPorClienteYEstado(codigo, "En espera"));
        model.addAttribute("listaCitas", servicioCita.buscarTodosPorClienteYEstado(codigo, estadoActivo));
        model.addAttribute("listaCitasFinalizados", servicioCita.buscarTodosPorClienteYEstado(codigo, "Terminado"));
        model.addAttribute("cliente", clienteDto);
        return "clientes";
    }

    @GetMapping({"/crear1/cita/{codigoCliente}"})
    public String mostrarFormularioCrear1(@PathVariable int codigoCliente, Model model){

        CitaDto citaDto = new CitaDto();

        model.addAttribute("listaServicios", servicioServicio.buscarTodos());
        model.addAttribute("listaSedes", servicioSede.buscarTodos());
        model.addAttribute("cita", citaDto);
        model.addAttribute("idCliente", codigoCliente);

        return "primer_crear_cita";
    }

    @GetMapping({"/crear2/cita/{idCliente}"})
    public String mostrarFormularioCrear(@PathVariable int idCliente, @ModelAttribute("cita") CitaDto citaDto, RedirectAttributes redirectAttributes){

        Servicio servicio = modelMapper.map(servicioServicio.buscarPorNombre(citaDto.getServicio().getNombre()),Servicio.class);
        Sede sede = modelMapper.map(servicioSede.buscarPorNombre(citaDto.getSede().getNombre()),Sede.class);

        citaDto.setSede(sede);
        citaDto.setServicio(servicio);

        LocalDateTime fechaActual = LocalDateTime.now();

        int anio = fechaActual.getYear();
        int mes = fechaActual.getMonthValue();
        int dia = fechaActual.getDayOfMonth();

        int hora = fechaActual.getHour() + 1;
        int minuto = fechaActual.getMinute();

        if(hora < 8){
            hora = 7;
            minuto = 30;
        }else if(hora < 16){
            minuto = minuto < 30 ? 0 : 30;
        }else{
            hora = 7;
            minuto = 30;

            Integer[] fechaActualizada = obtenerFechaActualizada(dia, mes, anio);

            dia = fechaActualizada[0];
            mes = fechaActualizada[1];
            anio = fechaActualizada[2];
        }

        redirectAttributes.addAttribute("anio", anio);
        redirectAttributes.addAttribute("mes", mes);
        redirectAttributes.addAttribute("dia", dia);
        redirectAttributes.addAttribute("hora", hora);
        redirectAttributes.addAttribute("minuto", minuto);
        redirectAttributes.addFlashAttribute("cita", citaDto);
        redirectAttributes.addAttribute("codigoCliente", idCliente);
        return "redirect:/calculo/fechas";
    }

    public Integer[] obtenerFechaActualizada(int dia, int mes, int anio){

        switch (mes) {
            case 4, 6, 9, 11:
                if (dia == 30) {
                    dia = 1;
                    mes += 1;
                } else {
                    dia += 1;
                }
                break;
            case 2:
                if (dia == 29 || dia == 28) {
                    dia = 1;
                    mes += 1;
                } else {
                    dia += 1;
                }
                break;
            case 12: // Diciembre
                if (dia == 31) {
                    dia = 1;
                    mes = 1;
                    anio += 1;
                } else {
                    dia += 1;
                }
                break;

            default: // Meses con 31 días
                if (dia == 31) {
                    dia = 1;
                    mes += 1;
                } else {
                    dia++;
                }
                break;
        }
        return new Integer[]{dia, mes, anio};
    }

    @GetMapping({"/calculo/fechas"})
    public String calculoFechas(@ModelAttribute("cita") CitaDto citaDto, @ModelAttribute("codigoCliente") int codigoCliente, @ModelAttribute("anio") int anio, @ModelAttribute("mes") int mes, @ModelAttribute("dia") int dia, @ModelAttribute("hora") int hora, @ModelAttribute("minuto") int minuto, Model model){

        Servicio servicio = citaDto.getServicio();
        Sede sede = citaDto.getSede();

        List<String> listaFechas = new ArrayList<>();

        while (hora < 16){

            if(minuto == 0){
                minuto += 30;
            }else {
                hora += 1;
                minuto = 0;
            }

            String fecha = String.format("%04d-%02d-%02d", anio, mes,dia);
            String tiempo = String.format("%02d:%02d", hora, minuto);

            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            TemporalAccessor temporalAccessor = formato.parse(fecha + " " + tiempo);

            LocalDateTime fechaActual = LocalDateTime.from(temporalAccessor);

            String fechaTabla = agregadoFechas(hora, codigoCliente, servicio, sede, fechaActual, fecha, tiempo);

            if(fechaTabla != null){
                listaFechas.add(fechaTabla);
            }
        }

        model.addAttribute("cita", citaDto);
        model.addAttribute("listaFechas", listaFechas);
        model.addAttribute("codigoCliente", codigoCliente);
        model.addAttribute("servicio", servicio);
        model.addAttribute("sede", sede);
        return "crear_cita";

    }

    public String agregadoFechas(int hora, int codigoCliente, Servicio servicio, Sede sede, LocalDateTime fechaActual, String fecha, String tiempo){
        String listaFecha = null;

        CitaDto citaDto1 = servicioCita.buscarCitaPorFechaPorSedePorClienteYEstado(fechaActual, sede.getIdSede(), codigoCliente, estadoActivo);
        CitaDto citaDto2 = servicioCita.buscarCitaPorFechaPorSedePorClienteYEstado(fechaActual, sede.getIdSede(), codigoCliente, estadoTerminado);
        CitaDto citaDto3 = servicioCita.buscarCitaPorFechaPorSedePorClienteYEstado(fechaActual, sede.getIdSede(), codigoCliente, estadoEspera);


        if(hora != 16 && citaDto1 == null && citaDto2 == null && citaDto3 == null){
            List<String> listadoFechas = servicioCita.buscarFechasPorServicioPorSedeYFecha(servicio.getIdServicio(), sede.getIdSede(), fechaActual);

            if(servicio.getNombre().equalsIgnoreCase("Caja")){
                if (listadoFechas.size() < 3){
                    listaFecha = fecha + " " + tiempo;
                }
            }else {
                if (listadoFechas.size() < 2){
                    listaFecha = fecha + " " + tiempo;
                }
            }
        }

        return listaFecha;
    }

    @PostMapping({"/crear/cita/{codigoCliente}"})
    public String crearCita(@PathVariable int codigoCliente, @ModelAttribute("cita") CitaDto citaDto, RedirectAttributes redirectAttributes){

        Servicio servicio = modelMapper.map(servicioServicio.buscarPorNombre(citaDto.getServicio().getNombre()),Servicio.class);
        Sede sede = modelMapper.map(servicioSede.buscarPorNombre(citaDto.getSede().getNombre()),Sede.class);

        Cliente cliente = modelMapper.map(servicioCliente.buscarPorPk(codigoCliente), Cliente.class);

        citaDto.setEstado(estadoActivo);
        citaDto.setNumTurno(servicioCita.buscarUltimoTurnoPorServicioYSede(servicio.getIdServicio(), sede.getIdSede()));
        citaDto.setCliente(cliente);
        citaDto.setSede(sede);
        citaDto.setServicio(servicio);


        servicioCita.crear(citaDto);

        redirectAttributes.addAttribute("codigo", cliente.getIdCliente());
        return "redirect:/principal/cliente/{codigo}";
    }


    @GetMapping({"/llamado/confirmado/{idCita}/{idEmpleado}"})
    public String confirmarLlamado(@PathVariable int idCita, @PathVariable int idEmpleado, RedirectAttributes redirectAttributes){

        CitaDto citaDto = servicioCita.buscarPorPk(idCita);
        EmpleadoDto empleadoDto = servicioEmpleado.buscarPorPk(idEmpleado);
        ClienteDto clienteDto = servicioCliente.buscarPorPk(citaDto.getCliente().getIdCliente());

        empleadoDto.setEstado("Ocupado");
        servicioEmpleado.modificar(empleadoDto);

        clienteDto.setEstado("Atendiendo");
        servicioCliente.modificar(clienteDto);

        citaDto.setCliente(modelMapper.map(clienteDto, Cliente.class));
        citaDto.setEmpleado(modelMapper.map(empleadoDto, Empleado.class));
        citaDto.setEstado("Atendiendo");
        citaDto.setHoraInicio(LocalTime.now());
        servicioCita.modificar(citaDto);

        redirectAttributes.addAttribute("idEmpleado", idEmpleado);
        return "redirect:/principal/empleado/{idEmpleado}";
    }

}

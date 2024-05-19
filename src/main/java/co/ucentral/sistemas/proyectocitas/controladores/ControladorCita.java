package co.ucentral.sistemas.proyectocitas.controladores;

import co.ucentral.sistemas.proyectocitas.entidades.Cliente;
import co.ucentral.sistemas.proyectocitas.entidades.Sede;
import co.ucentral.sistemas.proyectocitas.entidades.Servicio;
import co.ucentral.sistemas.proyectocitas.entidadesdto.CitaDto;
import co.ucentral.sistemas.proyectocitas.entidadesdto.ClienteDto;
import co.ucentral.sistemas.proyectocitas.servicios.ServicioCita;
import co.ucentral.sistemas.proyectocitas.servicios.ServicioCliente;
import co.ucentral.sistemas.proyectocitas.servicios.ServicioSede;
import co.ucentral.sistemas.proyectocitas.servicios.ServicioServicio;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

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

    public ControladorCita(ServicioCita servicioCita, ServicioSede servicioSede, ServicioServicio servicioServicio, ServicioCliente servicioCliente) {
        this.servicioCita = servicioCita;
        this.servicioSede = servicioSede;
        this.servicioServicio = servicioServicio;
        this.servicioCliente = servicioCliente;
    }

    @GetMapping("/principal/cliente/{codigo}")
    public String listarCitas(@PathVariable int codigo, Model model){

        ClienteDto clienteDto = servicioCliente.buscarPorPk(codigo);

        model.addAttribute("listaCitas", servicioCita.buscarTodosPorCliente(clienteDto));
        model.addAttribute("cliente", clienteDto);
        return "clientes";
    }

    @GetMapping({"/crear1/cita/{codigoCliente}"})
    public String mostrarFormularioCrear1(@PathVariable int codigoCliente, Model model){

        CitaDto citaDto = new CitaDto();

        model.addAttribute("listaServicios", servicioServicio.buscarTodos());
        model.addAttribute("listaSedes", servicioSede.buscarTodos());
        model.addAttribute("cita", citaDto);
        model.addAttribute("codigoCliente", codigoCliente);

        return "primer_crear_cita";
    }

    @GetMapping({"/crear2/cita/{codigoCliente}"})
    public String mostrarFormularioCrear(@PathVariable int codigoCliente, @ModelAttribute("cita") CitaDto citaDto, Model model){

        Servicio servicio = modelMapper.map(servicioServicio.buscarPorNombre(citaDto.getServicio().getNombre()),Servicio.class);
        Sede sede = modelMapper.map(servicioSede.buscarPorNombre(citaDto.getSede().getNombre()),Sede.class);


        LocalDateTime fechaActual = LocalDateTime.now();

        List<String> listaFechas = new ArrayList<>();

        String fecha;
        String tiempo;

        String estado = "Activa";

        String formatoFecha = "%04d-%02d-%02d";
        String formatoHora = "%02d:%02d";
        String formatoDate = "yyyy-MM-dd HH:mm";

        int anio = fechaActual.getYear();
        int mes = fechaActual.getMonthValue();
        int dia = fechaActual.getDayOfMonth();

        int hora = fechaActual.getHour() + 1;
        int minuto = fechaActual.getMinute();

        minuto  = minuto < 30 ? 0 : 30;

        if(hora < 8){

            hora = 7;
            minuto = 30;

            while(hora < 16){

                if (minuto == 0) {
                    minuto += 30;
                } else {
                    hora += 1;
                    minuto = 0;
                }

                fecha = String.format(formatoFecha, anio, mes,dia);
                tiempo = String.format(formatoHora, hora, minuto);

                DateTimeFormatter formato = DateTimeFormatter.ofPattern(formatoDate);

                TemporalAccessor temporalAccessor = formato.parse(fecha + " " + tiempo);

                fechaActual = LocalDateTime.from(temporalAccessor);

                CitaDto citaDto1= servicioCita.buscarCitaPorFechaPorSedePorClienteYEstado(fechaActual, sede.getIdSede(), codigoCliente, estado);

                if(hora != 16 && citaDto1 == null){

                    List<String> listadoFechas = servicioCita.buscarFechasPorServicioPorSedeYFecha(servicio.getIdServicio(), sede.getIdSede(), fechaActual);

                    if(servicio.getNombre().equalsIgnoreCase("Caja")){
                        if (listadoFechas.size() < 3){
                            listaFechas.add(fecha + " " + tiempo);
                        }
                    }else {
                        if (listadoFechas.size() < 2){
                            listaFechas.add(fecha + " " + tiempo);
                        }
                    }

                }
            }

        }else if(hora < 16){


            while(hora < 16){

                if(minuto == 0){
                    minuto += 30;
                }else {
                    hora += 1;
                    minuto = 0;
                }

                fecha = String.format(formatoFecha, anio, mes,dia);
                tiempo = String.format(formatoHora, hora, minuto);

                DateTimeFormatter formato = DateTimeFormatter.ofPattern(formatoDate);

                TemporalAccessor temporalAccessor = formato.parse(fecha + " " + tiempo);

                fechaActual = LocalDateTime.from(temporalAccessor);


                CitaDto citaDto1= servicioCita.buscarCitaPorFechaPorSedePorClienteYEstado(fechaActual, sede.getIdSede(), codigoCliente, estado);

                if(hora != 16 && citaDto1 == null){
                    List<String> listadoFechas = servicioCita.buscarFechasPorServicioPorSedeYFecha(servicio.getIdServicio(), sede.getIdSede(), fechaActual);

                    if(servicio.getNombre().equalsIgnoreCase("Caja")){
                        if (listadoFechas.size() < 3){
                            listaFechas.add(fecha + " " + tiempo);
                        }
                    }else {
                        if (listadoFechas.size() < 2){
                            listaFechas.add(fecha + " " + tiempo);
                        }
                    }
                }

            }
        }else{

            hora = 7;
            minuto = 30;

            switch (mes){
                case 4, 6, 9, 11:
                    if (dia == 30) {
                        dia = 1;
                        mes += 1;
                    } else {
                        dia +=1;
                    }
                    break;
                case 2: // Febrero
                    // Aquí puedes agregar una verificación adicional para años bisiestos si lo deseas

                    if (dia == 29 || dia == 28) {
                        dia = 1;
                        mes += 1;
                    }
                    else{
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

            while (hora < 16){

                if(minuto == 0){
                    minuto += 30;
                }else {
                    hora += 1;
                    minuto = 0;
                }

                fecha = String.format(formatoFecha, anio, mes,dia);
                tiempo = String.format(formatoHora, hora, minuto);

                DateTimeFormatter formato = DateTimeFormatter.ofPattern(formatoDate);

                TemporalAccessor temporalAccessor = formato.parse(fecha + " " + tiempo);

                fechaActual = LocalDateTime.from(temporalAccessor);

                CitaDto citaDto1= servicioCita.buscarCitaPorFechaPorSedePorClienteYEstado(fechaActual, sede.getIdSede(), codigoCliente, estado);

                if(hora != 16 && citaDto1 == null){
                    List<String> listadoFechas = servicioCita.buscarFechasPorServicioPorSedeYFecha(servicio.getIdServicio(), sede.getIdSede(), fechaActual);

                    if(servicio.getNombre().equalsIgnoreCase("Caja")){
                        if (listadoFechas.size() < 3){
                            listaFechas.add(fecha + " " + tiempo);
                        }
                    }else {
                        if (listadoFechas.size() < 2){
                            listaFechas.add(fecha + " " + tiempo);
                        }
                    }
                }
            }
        }

        model.addAttribute("cita", citaDto);
        model.addAttribute("listaFechas", listaFechas);
        model.addAttribute("codigoCliente", codigoCliente);
        model.addAttribute("servicio", servicio);
        model.addAttribute("sede", sede);
        return "crear_cita";
    }

    @PostMapping({"/crear/cita/{codigoCliente}"})
    public String crearCita(@PathVariable int codigoCliente, @ModelAttribute("cita") CitaDto citaDto, RedirectAttributes redirectAttributes){

        Servicio servicio = modelMapper.map(servicioServicio.buscarPorNombre(citaDto.getServicio().getNombre()),Servicio.class);
        Sede sede = modelMapper.map(servicioSede.buscarPorNombre(citaDto.getSede().getNombre()),Sede.class);

        Cliente cliente = modelMapper.map(servicioCliente.buscarPorPk(codigoCliente), Cliente.class);

        citaDto.setEstado("Activa");
        citaDto.setNumTurno(servicioCita.buscarUltimoTurnoPorServicioYSede(servicio.getIdServicio(), sede.getIdSede()));
        citaDto.setCliente(cliente);
        citaDto.setSede(sede);
        citaDto.setServicio(servicio);


        servicioCita.crear(citaDto);

        redirectAttributes.addAttribute("codigo", cliente.getIdCliente());
        return "redirect:/principal/cliente/{codigo}";
    }

}

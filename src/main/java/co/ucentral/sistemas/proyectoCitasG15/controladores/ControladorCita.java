package co.ucentral.sistemas.proyectoCitasG15.controladores;

import co.ucentral.sistemas.proyectoCitasG15.entidades.Cliente;
import co.ucentral.sistemas.proyectoCitasG15.entidades.Sede;
import co.ucentral.sistemas.proyectoCitasG15.entidades.Servicio;
import co.ucentral.sistemas.proyectoCitasG15.entidadesDto.CitaDto;
import co.ucentral.sistemas.proyectoCitasG15.entidadesDto.ClienteDto;
import co.ucentral.sistemas.proyectoCitasG15.entidadesDto.ServicioDto;
import co.ucentral.sistemas.proyectoCitasG15.servicios.ServicioCita;
import co.ucentral.sistemas.proyectoCitasG15.servicios.ServicioCliente;
import co.ucentral.sistemas.proyectoCitasG15.servicios.ServicioSede;
import co.ucentral.sistemas.proyectoCitasG15.servicios.ServicioServicio;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@Log4j2
public class ControladorCita {

    private final ModelMapper modelMapper = new ModelMapper();
    @Autowired
    ServicioCita servicioCita;

    @Autowired
    ServicioSede servicioSede;

    @Autowired
    ServicioServicio servicioServicio;

    @Autowired
    ServicioCliente servicioCliente;

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
    public String mostrarFormlarioCrear(@PathVariable int codigoCliente, @ModelAttribute("cita") CitaDto citaDto, Model model){

        /**
         * lista que almacena las 16 franjas de tiempo para la cita
         */
        List<String> listaFechas = new ArrayList<>();

        String tiempo;
        int hora = 7;
        int minuto = 30;

        DateTimeFormatter formatoDia = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String diaActual = formatoDia.format(LocalDate.now());


        /**
         * almacenado de las 16 franjas de tiempo del dia
         */
        for (int i = 0; i < 17; i++){

            if(minuto == 0){
                minuto += 30;
            }else {
                hora += 1;
                minuto = 0;
            }

            tiempo = String.format("%02d:%02d", hora, minuto);
            System.out.println(tiempo);
            listaFechas.add(diaActual + " " + tiempo);
        }

        model.addAttribute("cita", citaDto);
        model.addAttribute("listaFechas", listaFechas);
        model.addAttribute("codigoCliente", codigoCliente);
        model.addAttribute("servicio", citaDto.getServicio());
        model.addAttribute("sede", citaDto.getSede());
        return "crear_cita";
    }

    @PostMapping({"/crear/cita/{codigoCliente}"})
    public String crearCita(@PathVariable int codigoCliente, @ModelAttribute("cita") CitaDto citaDto, RedirectAttributes redirectAttributes){

        Servicio servicio = modelMapper.map(servicioServicio.buscarPorNombre(citaDto.getServicio().getNombre()),Servicio.class);
        Sede sede = modelMapper.map(servicioSede.buscarPorNombre(citaDto.getSede().getNombre()),Sede.class);

        Cliente cliente = modelMapper.map(servicioCliente.buscarPorPk(codigoCliente), Cliente.class);

        citaDto.setEstado("Activa");
        citaDto.setNumTurno((int) servicioCita.buscarUltimoTurnoPorServicioYSede(servicio.getIdServicio(), sede.getIdSede()));
        citaDto.setCliente(cliente);
        citaDto.setSede(sede);
        citaDto.setServicio(servicio);


        servicioCita.crear(citaDto);

        redirectAttributes.addAttribute("codigo", cliente.getIdCliente());
        return "redirect:/principal/cliente/{codigo}";
    }

}

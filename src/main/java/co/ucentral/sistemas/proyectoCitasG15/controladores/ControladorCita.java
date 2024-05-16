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
import java.time.LocalDateTime;
import java.time.LocalTime;
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
         * Obtención de la hora al momento que el cliente estara predispuesto a agendar la fecha de la cita
         */
        LocalDateTime fechaActual = LocalDateTime.now();



        /**
         * lista que almacena las 16 franjas de tiempo para la cita
         */
        List<String> listaFechas = new ArrayList<>();

        /**
         * Variables que permiten almacenar la fecha (YYYY/mm/dd) y la hora (HH:mm:ss) de las franjas que se visualizaran en la vista al cliente
         */
        String fecha;
        String tiempo;

        /**
         * Variables que almacenan el valor del dia, el mes y el año actual
         */
        int anio = fechaActual.getYear();
        int mes = fechaActual.getMonthValue();
        int dia = fechaActual.getDayOfMonth();;

        /**
         * funcionalidad de incremento sobre
         */
        int hora = fechaActual.getHour() + 1;
        int minuto = fechaActual.getMinute();

        if(minuto < 30){
            minuto  = 0;
        }else{
            minuto = 30;
        }

        if(hora < 8){

            hora = 7;
            minuto = 30;


            /**
             * almacenado de las 16 franjas de tiempo del dia
             */
            for (int i = 0; hora < 16; i++){

                if(minuto == 0){
                    minuto += 30;
                }else {
                    hora += 1;
                    minuto = 0;
                }

                fecha = String.format("%04d-%02d-%02d", anio, mes,dia);
                tiempo = String.format("%02d:%02d", hora, minuto);

                System.out.println(tiempo);


                if(hora != 16){
                    listaFechas.add(fecha + " " + tiempo);
                }
            }
        }else if(hora >=8 && hora < 16){
            /**
             * almacenado de las 16 franjas de tiempo del dia
             */
            for (int i = 0; hora < 16; i++){

                if(minuto == 0){
                    minuto += 30;
                }else {
                    hora += 1;
                    minuto = 0;
                }

                fecha = String.format("%04d-%02d-%02d", anio, mes,dia);
                tiempo = String.format("%02d:%02d", hora, minuto);

                System.out.println(tiempo);


                if(hora != 16){
                    listaFechas.add(fecha + " " + tiempo);
                }

            }
        }else{
            System.out.println("paso aqui");

            hora = 7;
            minuto = 30;

            switch (mes){
                case 4: case 6: case 9: case 11: // Meses con 30 días
                    if (dia == 30) {
                        dia = 1;
                        mes += 1;
                    } else {
                        dia +=1;
                    }
                    break;
                case 2: // Febrero
                    // Aquí puedes agregar una verificación adicional para años bisiestos si lo deseas

                    if (dia == 29) {
                        dia = 1;
                        mes += 1;

                    } else if(dia == 28) {
                        dia = 1;
                        mes += 1;
                    }else{
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

            for (int i = 0; hora < 16; i++){

                if(minuto == 0){
                    minuto += 30;
                }else {
                    hora += 1;
                    minuto = 0;
                }

                fecha = String.format("%04d-%02d-%02d", anio, mes,dia);
                tiempo = String.format("%02d:%02d", hora, minuto);

                System.out.println(tiempo);


                if(hora != 16){
                    listaFechas.add(fecha + " " + tiempo);
                }

            }

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

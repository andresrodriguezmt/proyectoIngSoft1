package co.ucentral.sistemas.proyectocitas.controladores;


import co.ucentral.sistemas.proyectocitas.entidades.Cliente;
import co.ucentral.sistemas.proyectocitas.entidades.Empleado;
import co.ucentral.sistemas.proyectocitas.entidadesdto.CitaDto;
import co.ucentral.sistemas.proyectocitas.entidadesdto.ClienteDto;
import co.ucentral.sistemas.proyectocitas.entidadesdto.EmpleadoDto;
import co.ucentral.sistemas.proyectocitas.servicios.ServicioCita;
import co.ucentral.sistemas.proyectocitas.servicios.ServicioCliente;
import co.ucentral.sistemas.proyectocitas.servicios.ServicioEmpleado;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ControladorEmpleado {

    private final ModelMapper modelMapper = new ModelMapper();

    ServicioEmpleado servicioEmpleado;
    ServicioCliente servicioCliente;
    ServicioCita servicioCita;

    public ControladorEmpleado(ServicioEmpleado servicioEmpleado, ServicioCita servicioCita, ServicioCliente servicioCliente) {
        this.servicioEmpleado = servicioEmpleado;
        this.servicioCita = servicioCita;
        this.servicioCliente = servicioCliente;
    }

    /**
     * Función que redireccióna a la pagina para realizar la autenticación de un empleado al proyecto
     *
     * @param error
     * @param model
     * @return
     */
    @GetMapping({"/empleado/autenticar"})
    public String visualizarAutenticar(@RequestParam(value = "error", required = false) String error, Model model){

        EmpleadoDto empleadoDto = new EmpleadoDto();
        model.addAttribute("empleadoAutenticar", empleadoDto);

        /**
         * valida si la alerta en la vista va a ser desplegada o no.
         */
        if (error != null) {
            model.addAttribute("loginError", true);
        }

        return "autenticacionEmpleado";
    }

    /**
     * Metodo para autenticar si el empleado existe a la aplicacion, donde en caso contrario
     * envia la determinada información para mostrar en la vista una alerta, que informa el posible
     * error en la digitación correcta de la cedula o la contraseña del empleado por parte del usuario.
     *
     * @param empleadoDto
     * @param model
     * @param redirectAttributes
     * @return
     */
    @PostMapping({"/empleado"})
    public String autenticacionUsuario(@ModelAttribute("empleadoAutenticar") EmpleadoDto empleadoDto, Model model, RedirectAttributes redirectAttributes){

        EmpleadoDto empleadoDto1 = servicioEmpleado.autenticarPorCedulayContrasenia(empleadoDto.getCedula(), empleadoDto.getContrasenia());

        if(empleadoDto1 != null){
            redirectAttributes.addAttribute("idEmpleado", empleadoDto1.getIdEmpleado());
            return "redirect:/principal/empleado/{idEmpleado}";
        }else{

            redirectAttributes.addFlashAttribute("alertaError", true);
            return "redirect:/empleado/autenticar";
        }
    }

    @GetMapping({"/principal/empleado/{idEmpleado}"})
    public String listarCitas(@PathVariable int idEmpleado, Model model){

        EmpleadoDto empleadoDto = servicioEmpleado.buscarPorPk(idEmpleado);

        model.addAttribute("empleado", empleadoDto);
        model.addAttribute("listaCitas", servicioCita.buscarTodosPorServicioPorSedeYEstado(empleadoDto.getServicio().getIdServicio(), empleadoDto.getSede().getIdSede(), "Activo"));
        model.addAttribute("listaCitasAtencion", servicioCita.buscarTodosPorServicioPorSedePorEstadoYEmpleado(empleadoDto.getServicio().getIdServicio(), empleadoDto.getSede().getIdSede(), "Atendiendo", idEmpleado));
        return "empleados";
    }

    @GetMapping({"/cita/llamado/{idCita}/{idEmpleado}"})
    public String ventanaLlamado(@PathVariable int idCita, @PathVariable int idEmpleado, Model model, RedirectAttributes redirectAttributes){

        CitaDto citaDto = servicioCita.buscarPorPk(idCita);
        EmpleadoDto empleadoDto = servicioEmpleado.buscarPorPk(idEmpleado);
        ClienteDto clienteDto = servicioCliente.buscarPorPk(citaDto.getCliente().getIdCliente());

        String estado = "En espera";

        empleadoDto.setEstado(estado);
        servicioEmpleado.modificar(empleadoDto);

        clienteDto.setEstado(estado);
        servicioCliente.modificar(clienteDto);

        citaDto.setCliente(modelMapper.map(clienteDto, Cliente.class));
        citaDto.setEmpleado(modelMapper.map(empleadoDto, Empleado.class));
        citaDto.setEstado(estado);
        servicioCita.modificar(citaDto);

        model.addAttribute("empleadoAsignado", idEmpleado);
        model.addAttribute("citaAsignada", idCita);
        return "llamadoEnSala";
    }




}

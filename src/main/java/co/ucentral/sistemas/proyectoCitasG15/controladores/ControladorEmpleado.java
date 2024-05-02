package co.ucentral.sistemas.proyectoCitasG15.controladores;


import co.ucentral.sistemas.proyectoCitasG15.entidadesDto.ClienteDto;
import co.ucentral.sistemas.proyectoCitasG15.entidadesDto.EmpleadoDto;
import co.ucentral.sistemas.proyectoCitasG15.servicios.ServicioEmpleado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ControladorEmpleado {

    @Autowired
    ServicioEmpleado servicioEmpleado;

    /**
     * Función que redireccióna a la pagina para realizar la autenticación al proyecto
     * @param error
     * @param model
     * @return
     */
    @GetMapping({"/usuario/autenticar"})
    public String visualizarAutenticar(@RequestParam(value = "error", required = false) String error, Model model){

        EmpleadoDto empleadoDto = new EmpleadoDto();
        model.addAttribute("empleado", empleadoDto);

        if (error != null) {
            model.addAttribute("loginError", true);
        }

        return "autenticacion";
    }

    /**
     * Metodo para autenticar si el empleado existe a la aplicacion, donde en caso contrario
     * validará si el usuario que intenta ingresar a la aplicación es un cliente, por lo tanto
     * redirecciona la información escrita en la vista a dicha funcionalidad en el controlador
     * de la entidad cliente
     *
     * @param empleadoDto
     * @param model
     * @param redirectAttributes
     * @return
     */
    @PostMapping({"/empleado"})
    public String autenticacionUsuario(@ModelAttribute("empleado") EmpleadoDto empleadoDto, Model model, RedirectAttributes redirectAttributes){
        if(servicioEmpleado.autenticarPorCedulayContrasenia(empleadoDto.getCedula(), empleadoDto.getContrasenia()) != null){

            EmpleadoDto empleadoDto1 = servicioEmpleado.autenticarPorCedulayContrasenia(empleadoDto.getCedula(), empleadoDto.getContrasenia());
            model.addAttribute("empleado", empleadoDto1);
            return "empleados";
        }else{

            ClienteDto clienteDto = new ClienteDto();

            clienteDto.setCorreo(empleadoDto.getCedula());
            clienteDto.setCedula(empleadoDto.getCedula());

            clienteDto.setContrasenia(empleadoDto.getContrasenia());

            redirectAttributes.addFlashAttribute("cliente", clienteDto);
            return "redirect:/cliente";
        }
    }
}

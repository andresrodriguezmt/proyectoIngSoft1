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
     * Función que redireccióna a la pagina para realizar la autenticación de un empleado al proyecto
     *
     * @param error
     * @param model
     * @return
     */
    @GetMapping({"/empleado/autenticar"})
    public String visualizarAutenticar(@RequestParam(value = "error", required = false) String error, Model model){

        EmpleadoDto empleadoDto = new EmpleadoDto();
        model.addAttribute("empleado", empleadoDto);

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
    public String autenticacionUsuario(@ModelAttribute("empleado") EmpleadoDto empleadoDto, Model model, RedirectAttributes redirectAttributes){

        EmpleadoDto empleadoDto1 = servicioEmpleado.autenticarPorCedulayContrasenia(empleadoDto.getCedula(), empleadoDto.getContrasenia());

        if(empleadoDto1 != null){
            model.addAttribute("empleado", empleadoDto1);
            return "empleados";
        }else{

            redirectAttributes.addFlashAttribute("alertaError", true);
            return "redirect:/empleado/autenticar";
        }
    }
}

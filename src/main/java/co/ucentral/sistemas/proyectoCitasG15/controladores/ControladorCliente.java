package co.ucentral.sistemas.proyectoCitasG15.controladores;

import co.ucentral.sistemas.proyectoCitasG15.entidadesDto.ClienteDto;
import co.ucentral.sistemas.proyectoCitasG15.entidadesDto.EmpleadoDto;
import co.ucentral.sistemas.proyectoCitasG15.servicios.ServicioCliente;
import org.hibernate.validator.constraints.Mod10Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ControladorCliente {

    @Autowired
    ServicioCliente servicioCliente;


    /**
     * Función que redireccióna a la correspondiente en la vista
     * para realizar la autenticación de un cliente al proyecto
     *
     * @param error
     * @param model
     * @return
     */
    @GetMapping({"/cliente/autenticar"})
    public String visualizarAutenticar(@RequestParam(value = "error", required = false) String error, Model model){

        ClienteDto clienteDto = new ClienteDto();

        model.addAttribute("cliente", clienteDto);

        if (error != null) {
            model.addAttribute("loginError", true);
        }

        return "autenticacionCliente";
    }

    /**
     * Funcion que con los datos traidos desde la vista del proyecto la determinada verificación
     * de la existencia de un cliente dentro de la base de datos del proyecto
     *
     * @param clienteDto
     * @param model
     * @param redirectAttributes
     * @return
     */
    @PostMapping({"/cliente"})
    public String visualizarAutenticar(@ModelAttribute("cliente") ClienteDto clienteDto, Model model, RedirectAttributes redirectAttributes){

        System.out.println(clienteDto.getCedula());
        ClienteDto clienteDto1 = servicioCliente.autenticarPorCedyContra(clienteDto.getCedula(), clienteDto.getContrasenia());

        if(clienteDto1 != null){

            model.addAttribute("cliente", clienteDto1);
            return "clientes";

        }else{

            ClienteDto clienteDto2 = servicioCliente.autenticarPorCorreoyContra(clienteDto.getCedula(), clienteDto.getContrasenia());

            if(clienteDto2 != null) {

                model.addAttribute("cliente", clienteDto2);
                return "clientes";
            }
            else{

                redirectAttributes.addFlashAttribute("alertaError", true);
                return "redirect:/cliente/autenticar";

            }
        }
    }
}

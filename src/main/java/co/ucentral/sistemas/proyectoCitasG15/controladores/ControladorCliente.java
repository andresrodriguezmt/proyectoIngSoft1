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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ControladorCliente {

    @Autowired
    ServicioCliente servicioCliente;

    @GetMapping({"/cliente"})
    public String visualizarAutenticar(@ModelAttribute("cliente") ClienteDto clienteDto, Model model, RedirectAttributes redirectAttributes){


        if(servicioCliente.autenticarPorCedyContra(clienteDto.getCedula(), clienteDto.getContrasenia()) != null){

            ClienteDto clienteDto1 = servicioCliente.autenticarPorCedyContra(clienteDto.getCedula(), clienteDto.getContrasenia());
            model.addAttribute("cliente", clienteDto1);
            return "clientes";

        }else if(servicioCliente.autenticarPorCorreoyContra(clienteDto.getCorreo(), clienteDto.getContrasenia()) != null){
            ClienteDto clienteDto1 = servicioCliente.autenticarPorCorreoyContra(clienteDto.getCorreo(), clienteDto.getContrasenia());
            model.addAttribute("cliente", clienteDto1);
            return "clientes";
        }else{
            redirectAttributes.addFlashAttribute("alertaError", true);
            return "redirect:/usuario/autenticar";
        }


    }
}

package co.ucentral.sistemas.proyectocitas.servicios;

import co.ucentral.sistemas.proyectocitas.entidades.*;
import co.ucentral.sistemas.proyectocitas.entidadesdto.HistorialClienteDto;
import co.ucentral.sistemas.proyectocitas.repositorios.RepositorioHistorialCliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ServicioHistorialClienteTest {

    private final ModelMapper modelMapper = new ModelMapper();
    private HistorialCliente historialCliente;

    @InjectMocks
    ServicioHistorialCliente servicioHistorialCliente;
    @Mock
    RepositorioHistorialCliente repositorioHistorialCliente;

    @BeforeEach
    void setUp(){
        Cliente cliente = Cliente
                .builder()
                .idCliente(1)
                .nombre("Gonzalo Perez Ramirez")
                .edad(29)
                .cedula("1005688954")
                .estado("Atendiendo")
                .contrasenia("perez775")
                .correo("gperezr@gmail.com")
                .build();

        Servicio servicio = Servicio
                .builder()
                .idServicio(1)
                .nombre("Servicio x")
                .build();

        Sede sede = Sede
                .builder()
                .idSede(1)
                .nombre("Sede z")
                .numEmpleado(7)
                .direccion("Calle 45 #65 -12 sur")
                .build();

        sede.setHoraApertura(8,0,0);
        sede.setHoraCierre(16,0,0);

        Empleado empleado = Empleado
                .builder()
                .idEmpleado(1)
                .nombre("Andres Felipe Rodriguez")
                .edad(35)
                .cedula("1008544697")
                .contrasenia("Rodriguez789")
                .estado("Ocupado")
                .sede(sede)
                .servicio(servicio)
                .build();

        Cita cita = Cita
                .builder()
                .idCita(1)
                .numTurno(1)
                .estado("Atendiendo")
                .empleado(empleado)
                .sede(sede)
                .servicio(servicio)
                .cliente(cliente)
                .build();
        cita.setFecha(2024,5,21,10,0,0);
        cita.setHoraInicio(10,0,0);

        historialCliente = HistorialCliente
                .builder()
                .idHistorialCliente(1)
                .observaciones("Se han respondido todas las preguntas que tuvo el cliente de forma acertada")
                .cita(cita)
                .build();

        historialCliente.getCita().setEstado("Terminado");
        historialCliente.getCita().getCliente().setEstado("Libre");
        historialCliente.getCita().getEmpleado().setEstado("Libre");

        repositorioHistorialCliente.save(historialCliente);

    }

    @Test
    @DisplayName("Test para crear un historial de cliente")
    void testCrear() {
        //given
        given(repositorioHistorialCliente.save(historialCliente)).willReturn(historialCliente);

        //when
        HistorialClienteDto historialClienteDto = servicioHistorialCliente.crear(modelMapper.map(historialCliente, HistorialClienteDto.class));

        //then
        assertThat(historialClienteDto).isNotNull();
    }

    @Test
    @DisplayName("Test para traer todos los historial cliente existentes")
    void testBuscarTodos() {

        //given
        given(repositorioHistorialCliente.findAll()).willReturn(List.of(historialCliente));

        //when
        List<HistorialClienteDto> listaHistorial = servicioHistorialCliente.buscarTodos();

        //then
        assertThat(listaHistorial).hasSize(1);

    }
}
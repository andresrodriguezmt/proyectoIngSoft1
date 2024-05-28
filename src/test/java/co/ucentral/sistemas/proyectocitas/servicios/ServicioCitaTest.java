package co.ucentral.sistemas.proyectocitas.servicios;

import co.ucentral.sistemas.proyectocitas.entidades.*;
import co.ucentral.sistemas.proyectocitas.entidadesdto.CitaDto;
import co.ucentral.sistemas.proyectocitas.entidadesdto.ClienteDto;
import co.ucentral.sistemas.proyectocitas.repositorios.RepositorioCita;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ServicioCitaTest {

    private final ModelMapper modelMapper = new ModelMapper();

    private Cita cita;

    @InjectMocks
    ServicioCita servicioCita;
    @Mock
    RepositorioCita repositorioCita;

    @BeforeEach
    void setUp(){

        Cliente cliente = Cliente
                .builder()
                .idCliente(1)
                .nombre("Gonzalo Perez Ramirez")
                .edad(29)
                .cedula("1005688954")
                .estado("Libre")
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
                .estado("Libre")
                .sede(sede)
                .servicio(servicio)
                .build();

        cita = Cita
                .builder()
                .idCita(1)
                .numTurno(1)
                .estado("Activo")
                .empleado(empleado)
                .sede(sede)
                .servicio(servicio)
                .cliente(cliente)
                .build();
        cita.setFecha(2024,5,21,10,0,0);
        cita.setHoraInicio(10,0,0);

        repositorioCita.save(cita);
    }


    @Test
    @DisplayName("Test para crear una cita")
    void testCrear() {
        //given
        given(repositorioCita.save(cita)).willReturn(cita);

        //when
        CitaDto citaDto = servicioCita.crear(modelMapper.map(cita, CitaDto.class));

        //then
        assertThat(citaDto).isNotNull();
    }

    @Test
    @DisplayName("Test lista de citas vacia")
    void testListaCitasVacia() {

        //given
        given(repositorioCita.findAll()).willReturn(Collections.emptyList());

        //when
        List<CitaDto> listaCitas = servicioCita.buscarTodos();

        //then
        assertThat(listaCitas).isEmpty();

    }

    @Test
    @DisplayName("Test para buscar una cita por su pk")
    void testBuscarPorPk() {

        //given
        given(repositorioCita.findById(cita.getIdCita())).willReturn(Optional.of(cita));

        //when
        CitaDto citaDto = servicioCita.buscarPorPk(cita.getIdCita());

        //then
        assertThat(citaDto).isNotNull();
    }

    @Test
    @DisplayName("Test para modificar una cita")
    void testModificar() {

        //given
        given(repositorioCita.findById(1)).willReturn(Optional.of(cita));
        given(repositorioCita.save(cita)).willReturn(cita);
        cita.setNumTurno(2);

        //when
        CitaDto citaDto = servicioCita.modificar(modelMapper.map(cita, CitaDto.class));

        //then
        assertThat(citaDto.getNumTurno()).isEqualTo(2);
    }

    @Test
    @DisplayName("Test para traer todas la citas por cliente")
    void testBuscarTodosPorCliente() {
        //given
        given(repositorioCita.findAllByCliente(cita.getCliente())).willReturn(List.of(cita));
        //when
        List<CitaDto> listaCitas = servicioCita.buscarTodosPorCliente(modelMapper.map(cita.getCliente(), ClienteDto.class));

        //then
        assertThat(listaCitas).isNotNull();
    }

    @Test
    @DisplayName("Test para traer todas la citas por fecha")
    void testBuscarTodosPorFecha() {
        //given
        given(repositorioCita.findAllByFecha(cita.getFecha())).willReturn(List.of(cita));
        //when
        List<CitaDto> listaCitas = servicioCita.buscarTodosPorFecha(cita.getFecha());

        //then
        assertThat(listaCitas).isNotNull();
    }

    @Test
    @DisplayName("Test para buscar el ultimo turno por servicio y sede")
    void testBuscarUltimoTurnoPorServicioYSede() {
        //given
        given(repositorioCita.findLastNumTurnoByServicioAndSede(cita.getServicio().getIdServicio(), cita.getSede().getIdSede())).willReturn(cita.getNumTurno());

        //when
        Integer numero = servicioCita.buscarUltimoTurnoPorServicioYSede(cita.getServicio().getIdServicio(), cita.getSede().getIdSede());

        //then
        assertThat(numero).isEqualTo(2);
    }

    @Test
    @DisplayName("Test para bucar una cita por fecha, sede, servicio, cliente y estado")
    void testBuscarCitaPorFechaPorSedePorClienteYEstado() {
        //given
        given(repositorioCita.findCitaByFechaBySedeByClienteAndEstado(cita.getFecha(),cita.getSede().getIdSede(), cita.getCliente().getIdCliente(), cita.getEstado())).willReturn(cita);

        //when
        CitaDto citaDto = servicioCita.buscarCitaPorFechaPorSedePorClienteYEstado(cita.getFecha(),cita.getSede().getIdSede(), cita.getCliente().getIdCliente(), cita.getEstado());

        //then
        assertThat(citaDto).isNotNull();
    }

    @Test
    @DisplayName("Test pata buscar lista de fechas por servicio, por sede y por fecha ")
    void testBuscarFechasPorServicioPorSedeYFecha() {
        //given
        given(repositorioCita.findFechasByServicioBySedeAndFecha(cita.getServicio().getIdServicio(), cita.getSede().getIdSede(), cita.getFecha())).willReturn(List.of(String.valueOf(cita.getFecha())));

        //when
        List<String> listaFechas = servicioCita.buscarFechasPorServicioPorSedeYFecha(cita.getServicio().getIdServicio(), cita.getSede().getIdSede(), cita.getFecha());

        //then
        assertThat(listaFechas).isNotNull();

    }

    @Test
    @DisplayName("Test para buscar todas las citas por servicio, por sede y por estado")
    void testBuscarTodosPorServicioPorSedeYEstado() {
        //given
        given(repositorioCita.findAllByServicioBySedeAndEstado(cita.getServicio().getIdServicio(), cita.getSede().getIdSede(), cita.getEstado())).willReturn(List.of(cita));

        //when
        List<CitaDto> listaCitas = servicioCita.buscarTodosPorServicioPorSedeYEstado(cita.getServicio().getIdServicio(), cita.getSede().getIdSede(), cita.getEstado());

        //then
        assertThat(listaCitas).hasSize(1);
    }

    @Test
    @DisplayName("Test para buscar todas las citas por id del cliente y estado ")
    void testBuscarTodosPorClienteYEstado() {

        //given
        given(repositorioCita.findAllByClienteAndEstado(cita.getCliente().getIdCliente(), cita.getEstado())).willReturn(List.of(cita));

        //when
        List<CitaDto> listaCitas = servicioCita.buscarTodosPorClienteYEstado(cita.getCliente().getIdCliente(), cita.getEstado());

        //then
        assertThat(listaCitas).hasSize(1);
    }

    @Test
    @DisplayName("Test para buscar todas las citas por servicio, por sede, por estado y por empleado")
    void testBuscarTodosPorServicioPorSedePorEstadoYEmpleado() {

        //given
        given(repositorioCita.findAllByServicioBySedeByEstadoAndEmpleado(cita.getServicio().getIdServicio(), cita.getSede().getIdSede(),cita.getEstado(), cita.getEmpleado().getIdEmpleado())).willReturn(List.of(cita));

        //when
        List<CitaDto> listaCitas = servicioCita.buscarTodosPorServicioPorSedePorEstadoYEmpleado(cita.getServicio().getIdServicio(), cita.getSede().getIdSede(),cita.getEstado(), cita.getEmpleado().getIdEmpleado());

        //then
        assertThat(listaCitas).hasSize(1);
    }


}
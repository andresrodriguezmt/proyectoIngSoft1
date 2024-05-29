package co.ucentral.sistemas.proyectocitas.servicios;

import co.ucentral.sistemas.proyectocitas.entidades.Empleado;
import co.ucentral.sistemas.proyectocitas.entidades.Sede;
import co.ucentral.sistemas.proyectocitas.entidades.Servicio;
import co.ucentral.sistemas.proyectocitas.entidadesdto.EmpleadoDto;
import co.ucentral.sistemas.proyectocitas.repositorios.RepositorioEmpleado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;


import java.util.Optional;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ServicioEmpleadoTest {

    private final ModelMapper modelMapper = new ModelMapper();
    private Empleado empleado;

    @InjectMocks
    ServicioEmpleado servicioEmpleado;
    @Mock
    RepositorioEmpleado repositorioEmpleado;

    @BeforeEach
    void setUp(){
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

        empleado = Empleado
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

        repositorioEmpleado.save(empleado);
    }
    @Test
    @DisplayName("Test para guardan un carro")
    void testCrear() {

        //given
        given(repositorioEmpleado.save(empleado)).willReturn(empleado);

        //when
        EmpleadoDto empleado1 = servicioEmpleado.crear(modelMapper.map(empleado, EmpleadoDto.class));


        //then
        assertThat(empleado1).isNotNull();

    }

    @Test
    @DisplayName("Test autenticar empleado por cedula y contraseña")
    void testAutenticarPorCedulayContrasenia() {
        //given
        given(repositorioEmpleado.buscarEmpPorCedulayContrasenia(empleado.getCedula(), empleado.getContrasenia())).willReturn(empleado);

        //when
        EmpleadoDto empleadoDto1 = servicioEmpleado.autenticarPorCedulayContrasenia(empleado.getCedula(), empleado.getContrasenia());

        //then
        assertThat(empleadoDto1).isNotNull();
    }

    @Test
    @DisplayName("Test buscar un empleado por su llave primaria")
    void testBuscarPorPk() {
        //given
        given(repositorioEmpleado.findById(1)).willReturn(Optional.of(empleado));

        //when
        EmpleadoDto empleadoDto1 = servicioEmpleado.buscarPorPk(empleado.getIdEmpleado());

        //then
        assertThat(empleadoDto1).isNotNull();
    }

    @Test
    @DisplayName("Test modificar empleado")
    void testModificar() {
        //given
        given(repositorioEmpleado.findById(1)).willReturn(Optional.of(empleado));
        given(repositorioEmpleado.save(empleado)).willReturn(empleado);
        empleado.setNombre("Mauricio Gonzales Perez");
        empleado.setContrasenia("gonzales349");

        //when
        EmpleadoDto empleadoDto = servicioEmpleado.modificar(modelMapper.map(empleado, EmpleadoDto.class));

        //then
        assertThat(empleadoDto.getNombre()).isEqualTo("Mauricio Gonzales Perez");
        assertThat(empleadoDto.getContrasenia()).isEqualTo("gonzales349");
    }
}
package co.ucentral.sistemas.proyectocitas.servicios;

import co.ucentral.sistemas.proyectocitas.entidades.Cliente;
import co.ucentral.sistemas.proyectocitas.entidadesdto.ClienteDto;
import co.ucentral.sistemas.proyectocitas.entidadesdto.EmpleadoDto;
import co.ucentral.sistemas.proyectocitas.repositorios.RepositorioCliente;
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
class ServicioClienteTest {

    private final ModelMapper modelMapper = new ModelMapper();

    private Cliente cliente;

    @InjectMocks
    ServicioCliente servicioCliente;
    @Mock
    RepositorioCliente repositorioCliente;
    @BeforeEach
    void setUp(){

        cliente = Cliente
                .builder()
                .idCliente(1)
                .nombre("Andres Felipe Rodriguez")
                .edad(35)
                .cedula("1008542297")
                .correo("rodriguez875")
                .contrasenia("Rodriguez965")
                .estado("Libre")
                .build();

        repositorioCliente.save(cliente);
    }

    @Test
    @DisplayName("Test para crear un cliente")
    void testCrear() {

        //given
        given(repositorioCliente.save(cliente)).willReturn(cliente);

        //when
        ClienteDto clienteDto = servicioCliente.crear(modelMapper.map(cliente, ClienteDto.class));

        //then
        assertThat(clienteDto).isNotNull();

    }

    @Test
    @DisplayName("Test para verificar existencia del cliente por cedula y contraseña")
    void testAutenticarPorCedyContra() {
        //given
        given(repositorioCliente.buscarCliePorCedulayContrasenia(cliente.getCedula(),cliente.getContrasenia())).willReturn(cliente);

        //when
        ClienteDto clienteDto = servicioCliente.autenticarPorCedyContra(cliente.getCedula(), cliente.getContrasenia());

        //then
        assertThat(clienteDto).isNotNull();
    }

    @Test
    @DisplayName("Test para verificar existencia de cliente por correo y contraseña")
    void testAutenticarPorCorreoyContra() {
        //given
        given(repositorioCliente.buscarCliePorCorreoyContrasenia(cliente.getCorreo(),cliente.getContrasenia())).willReturn(cliente);

        //when
        ClienteDto clienteDto = servicioCliente.autenticarPorCorreoyContra(cliente.getCorreo(), cliente.getContrasenia());

        //then
        assertThat(clienteDto).isNotNull();
    }

    @Test
    @DisplayName("Test para buscar cliente por su llave primaria")
    void testBuscarPorPk() {
        //given
        given(repositorioCliente.findById(1)).willReturn(Optional.of(cliente));

        //when
        ClienteDto clienteDto = servicioCliente.buscarPorPk(cliente.getIdCliente());

        //then
        assertThat(clienteDto).isNotNull();
    }

    @Test
    @DisplayName("Test para modificar un cliente")
    void testModificar() {
        //given
        given(repositorioCliente.findById(1)).willReturn(Optional.of(cliente));
        given(repositorioCliente.save(cliente)).willReturn(cliente);
        cliente.setNombre("Fernando Gonzales");
        cliente.setCorreo("gonzales@gmail.com");
        cliente.setContrasenia("gonzales743");

        //when
        ClienteDto clienteDto = servicioCliente.modificar(modelMapper.map(cliente, ClienteDto.class));

        //then
        assertThat(clienteDto.getNombre()).isEqualTo("Fernando Gonzales");
        assertThat(clienteDto.getCorreo()).isEqualTo("gonzales@gmail.com");
        assertThat(clienteDto.getContrasenia()).isEqualTo("gonzales743");
    }
}
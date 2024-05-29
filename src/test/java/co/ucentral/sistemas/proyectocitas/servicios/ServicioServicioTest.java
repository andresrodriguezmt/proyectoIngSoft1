package co.ucentral.sistemas.proyectocitas.servicios;

import co.ucentral.sistemas.proyectocitas.entidades.Servicio;
import co.ucentral.sistemas.proyectocitas.entidadesdto.ServicioDto;
import co.ucentral.sistemas.proyectocitas.repositorios.RepositorioCliente;
import co.ucentral.sistemas.proyectocitas.repositorios.RepositorioServicio;
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
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ServicioServicioTest {

    private final ModelMapper modelMapper = new ModelMapper();

    private Servicio servicio;

    @InjectMocks
    ServicioServicio servicioServicio;
    @Mock
    RepositorioServicio repositorioServicio;

    @BeforeEach
    void setUp(){
        servicio = Servicio
                .builder()
                .idServicio(1)
                .nombre("Servicio nuevo")
                .build();

        repositorioServicio.save(servicio);
    }


    @Test
    @DisplayName("Test para crear un servicio")
    void testCrear() {
        //given
        given(repositorioServicio.save(servicio)).willReturn(servicio);

        //when
        ServicioDto servicioDto = servicioServicio.crear(modelMapper.map(servicio, ServicioDto.class));

        //then
        assertThat(servicioDto).isNotNull();
    }

    @Test
    @DisplayName("Test para buscar todos los servicios en la base de datos")
    void testBuscarTodos() {
        //given
        given(repositorioServicio.findAll()).willReturn(List.of(servicio));

        //when
        List<ServicioDto> listaServicios = servicioServicio.buscarTodos();

        //then
        assertThat(listaServicios).hasSize(1);

    }

    @Test
    @DisplayName("Test para traer el servicio por el nombre")
    void testBuscarPorNombre() {
        //given
        given(repositorioServicio.findByNombre(servicio.getNombre())).willReturn(servicio);

        //when
        ServicioDto servicioDto = servicioServicio.buscarPorNombre(servicio.getNombre());

        //then
        assertThat(servicioDto).isNotNull();
    }
}
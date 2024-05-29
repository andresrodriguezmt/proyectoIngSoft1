package co.ucentral.sistemas.proyectocitas.servicios;

import co.ucentral.sistemas.proyectocitas.entidades.Sede;
import co.ucentral.sistemas.proyectocitas.entidadesdto.SedeDto;
import co.ucentral.sistemas.proyectocitas.repositorios.RepositorioSede;
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
class ServicioSedeTest {

    private  final ModelMapper modelMapper = new ModelMapper();

    private Sede sede;

    @InjectMocks
    ServicioSede servicioSede;
    @Mock
    RepositorioSede repositorioSede;

    @BeforeEach
    void setUp(){
        sede = Sede
                .builder()
                .idSede(1)
                .nombre("Sede nueva")
                .numEmpleado(7).direccion("Calle 45 #65 - 12 sur")
                .build();

        repositorioSede.save(sede);
    }

    @Test
    @DisplayName("Test para crear una sede")
    void testCrear() {

        //given
        given(repositorioSede.save(sede)).willReturn(sede);

        //when
        SedeDto sedeDto = servicioSede.crear(modelMapper.map(sede, SedeDto.class));

        //then
        assertThat(sedeDto).isNotNull();
    }

    @Test
    @DisplayName("test para buscar todas las sedes ")
    void testBuscarTodos() {
        //given
        given(repositorioSede.findAll()).willReturn(List.of(sede));

        //when
        List<SedeDto> listaSedes = servicioSede.buscarTodos();

        //then
        assertThat(listaSedes).hasSize(1);
    }

    @Test
    @DisplayName("Test para buscar una sede por su nombre")
    void testBuscarPorNombre() {
        //given
        given(repositorioSede.findByNombre(sede.getNombre())).willReturn(sede);

        //when
        SedeDto sedeDto = servicioSede.buscarPorNombre(sede.getNombre());

        //then
        assertThat(sedeDto).isNotNull();
    }
}
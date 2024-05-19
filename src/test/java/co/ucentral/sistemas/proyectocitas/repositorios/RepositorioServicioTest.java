package co.ucentral.sistemas.proyectocitas.repositorios;

import co.ucentral.sistemas.proyectocitas.entidades.Servicio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RepositorioServicioTest {

    @Autowired
    RepositorioServicio repositorioServicio;

    @Test
    @DisplayName("Prueba para crear un servicio")
    void testCrearServicio(){

        Servicio servicio = Servicio
                .builder()
                .nombre("Asesoria y caja")
                .build();


        Servicio servicio1 = repositorioServicio.save(servicio);

        assertThat(servicio1).isNotNull();

        assertThat(servicio1.getIdServicio()).isPositive();
    }


    @Test
    @DisplayName("Prueba para listar servicios")
    void testListarServicios(){
        List<Servicio> listaServicios = repositorioServicio.findAll();

        assertThat(listaServicios).isNotNull();
    }

    @Test
    @DisplayName("Prueba para listar servicios, creando 2 servicios")
    void testListarServiciosConDatos(){

        Servicio servicio = Servicio
                .builder()
                .nombre("Caja y asesoria")
                .build();

        Servicio servicio1 = Servicio
                .builder()
                .nombre("Obtener productos y caja")
                .build();

        repositorioServicio.save(servicio);
        repositorioServicio.save(servicio1);

        List<Servicio> listaServicios = repositorioServicio.findAll();

        assertThat(listaServicios).isNotNull();
    }

    @Test
    @DisplayName("Test para buscar un servicio por su llave primaria")
    void testBuscarServicioPorPk(){

        Servicio servicio = repositorioServicio.getReferenceById(1);

        assertThat(servicio).isNotNull();
    }

    @Test
    @DisplayName("Test para actualizar un Servicio")
    void testActualizarServicio(){
        Servicio servicio = repositorioServicio.getReferenceById(1);

        servicio.setNombre("Asesoria, caja y obtener nuevos productos");

        repositorioServicio.save(servicio);

        Servicio servicio1 = repositorioServicio.getReferenceById(1);

        assertThat(servicio1.getNombre()).isEqualTo(servicio.getNombre());
    }

    @Test
    @DisplayName("Test para eliminar un servicio")
    void testEliminarServicio(){

        Servicio servicio = Servicio
                .builder()
                .nombre("Caja y asesoria")
                .build();

        Servicio servicio1 = repositorioServicio.save(servicio);

        repositorioServicio.delete(servicio1);

        Servicio servicio2 = repositorioServicio.findById(servicio1.getIdServicio()).orElse(null);

        assertThat(servicio2).isNull();
    }

    @Test
    @DisplayName("Test para eliminar un servicio por su llave primaria")
    void testEliminarServicioPorPk(){

        Servicio servicio = Servicio
                .builder()
                .nombre("Caja y asesoria")
                .build();

        Servicio servicio1 = repositorioServicio.save(servicio);

        repositorioServicio.deleteById(servicio1.getIdServicio());

        Servicio servicio2 = repositorioServicio.findById(servicio1.getIdServicio()).orElse(null);

        assertThat(servicio2).isNull();
    }


    @Test
    @DisplayName("Prueba para traer un servicio por su nombre")
    void testBuscarServicioPorNombre() {

        Servicio servicio = Servicio
                .builder()
                .nombre("Caja y asesoria")
                .build();

        repositorioServicio.save(servicio);

        Servicio servicio1= repositorioServicio.findByNombre(servicio.getNombre());

        assertThat(servicio1).isNotNull();
        assertThat(servicio1.getNombre()).isEqualTo(servicio.getNombre());
    }
}
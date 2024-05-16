package co.ucentral.sistemas.proyectoCitasG15.repositorios;

import co.ucentral.sistemas.proyectoCitasG15.entidades.Empleado;
import co.ucentral.sistemas.proyectoCitasG15.entidades.Sede;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RepositorioSedeTest {

    @Autowired
    RepositorioSede repositorioSede;

    @Test
    @DisplayName("Prueba para crear un empleado")
    void testCrearSede(){

        Sede sede = Sede
                .builder()
                .nombre("Zona nororiente")
                .direccion("Calle 65 #76-68 norte")
                .numEmpleado(45)
                .build();
        sede.setHoraApertura(8,0,0);
        sede.setHoraCierre(16,0,0);

        Sede sede1 = repositorioSede.save(sede);

        assertThat(sede1).isNotNull();

        assertThat(sede1.getIdSede()).isGreaterThan(0);
    }


    @Test
    @DisplayName("Prueba para listar sedes")
    void testListarSedes(){
        List<Sede> listaSedes = repositorioSede.findAll();

        assertThat(listaSedes.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("Prueba para listar sedes, ingresando 2 sedes")
    void testListarSedesConDatos(){

        Sede sede = Sede
                .builder()
                .nombre("Zona nororiente")
                .direccion("Calle 65 #76-68 norte")
                .numEmpleado(45)
                .build();
        sede.setHoraApertura(8,0,0);
        sede.setHoraCierre(16,0,0);

        Sede sede1 = Sede
                .builder()
                .nombre("Zona suroriente")
                .direccion("Calle 65 #66-98 Sur")
                .numEmpleado(21)
                .build();
        sede1.setHoraApertura(8,0,0);
        sede1.setHoraCierre(16,0,0);

        repositorioSede.save(sede);
        repositorioSede.save(sede1);

        List<Sede> listaSedes = repositorioSede.findAll();

        assertThat(listaSedes.isEmpty()).isFalse();
        assertThat(listaSedes.size()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Test para buscar sede por su llave primaria")
    void testBuscarSedePorPk(){

        Sede sede = repositorioSede.getReferenceById(1);

        assertThat(sede).isNotNull();
    }

    @Test
    @DisplayName("Test para actualizar una sede")
    void testActualizarSede(){
        Sede sede = repositorioSede.getReferenceById(1);

        sede.setNombre("Zona centro");
        sede.setDireccion("Calle 65 #78-96");
        sede.setNumEmpleado(20);

        repositorioSede.save(sede);

        Sede sede1 = repositorioSede.getReferenceById(1);

        assertThat(sede1.getNombre()).isEqualTo(sede.getNombre());
        assertThat(sede1.getDireccion()).isEqualTo(sede.getDireccion());
        assertThat(sede1.getNumEmpleado()).isEqualTo(sede.getNumEmpleado());
    }

    @Test
    @DisplayName("Test para eliminar una Sede")
    void testEliminarSede(){

        Sede sede = Sede
                .builder()
                .nombre("Zona nororiente")
                .direccion("Calle 65 #76-68 norte")
                .numEmpleado(45)
                .build();
        sede.setHoraApertura(8,0,0);
        sede.setHoraCierre(16,0,0);

        Sede sede1 = repositorioSede.save(sede);

        repositorioSede.delete(sede1);

        Sede sede2 = repositorioSede.findById(sede1.getIdSede()).orElse(null);

        assertThat(sede2).isNull();
    }

    @Test
    @DisplayName("Test para eliminar una sede por su llave primaria")
    void testEliminarSedePorPk(){

        Sede sede = Sede
                .builder()
                .nombre("Zona nororiente")
                .direccion("Calle 65 #76-68 norte")
                .numEmpleado(45)
                .build();
        sede.setHoraApertura(8,0,0);
        sede.setHoraCierre(16,0,0);

        Sede sede1 = repositorioSede.save(sede);

        repositorioSede.deleteById(sede1.getIdSede());

        Sede sede2= repositorioSede.findById(sede1.getIdSede()).orElse(null);

        assertThat(sede2).isNull();
    }


    @Test
    @DisplayName("Prueba para traer una sede por su nombre")
    void testBuscarSedePorNombre() {

        Sede sede = Sede
                .builder()
                .nombre("Zona nororiente")
                .direccion("Calle 65 #76-68 norte")
                .numEmpleado(45)
                .build();
        sede.setHoraApertura(8,0,0);
        sede.setHoraCierre(16,0,0);

        repositorioSede.save(sede);

        Sede sede1 = repositorioSede.findByNombre(sede.getNombre());

        assertThat(sede1).isNotNull();
        assertThat(sede1.getNombre()).isEqualTo(sede.getNombre());
    }
}
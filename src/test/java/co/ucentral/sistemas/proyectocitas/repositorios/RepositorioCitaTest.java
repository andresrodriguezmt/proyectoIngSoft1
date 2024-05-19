package co.ucentral.sistemas.proyectocitas.repositorios;

import co.ucentral.sistemas.proyectocitas.entidades.Cita;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RepositorioCitaTest {

    @Autowired
    RepositorioCita repositorioCita;

    @Test
    @DisplayName("Prueba para crear una cita")
    void testCrearCita(){

        Cita cita = Cita
                .builder()
                .numTurno(1)
                .estado("Activa")
                .build();

        LocalDateTime fechaActual = LocalDateTime.now();
        cita.setFecha(fechaActual);

        Cita cita1 = repositorioCita.save(cita);

        assertThat(cita1).isNotNull();

        assertThat(cita1.getIdCita()).isPositive();
    }

    @Test
    @DisplayName("Prueba para listar citas")
    void testListarCitas(){
        List<Cita> listaCitas = repositorioCita.findAll();

        if(listaCitas.isEmpty()){
            listaCitas = null;
        }

        assertThat(listaCitas).isNull();
    }

    @Test
    @DisplayName("Prueba para listar citas, ingresando 2 citas")
    void testListarCitasConDatos(){

        Cita cita = Cita
                .builder()
                .numTurno(1)
                .estado("Activa")
                .build();

        LocalDateTime fechaActual = LocalDateTime.now();
        cita.setFecha(fechaActual);

        Cita cita1 = Cita
                .builder()
                .numTurno(1)
                .estado("Activa")
                .build();

        cita1.setFecha(fechaActual);

        repositorioCita.save(cita);
        repositorioCita.save(cita1);

        List<Cita> listaCitas = repositorioCita.findAll();

        assertThat(listaCitas).isNotNull();
    }

    @Test
    @DisplayName("Test para buscar cliente por su llave primaria")
    void testBuscarCitasPorPk(){

        Cita cita = Cita
                .builder()
                .numTurno(1)
                .estado("Activa")
                .build();

        LocalDateTime fechaActual = LocalDateTime.now();
        cita.setFecha(fechaActual);

        Cita cita1 = repositorioCita.getReferenceById(1);

        assertThat(cita1).isNotNull();
    }

    @Test
    @DisplayName("Test para actualizar una cita")
    void testActualizarCita(){

        Cita cita = Cita
                .builder()
                .numTurno(1)
                .estado("Activa")
                .build();

        LocalDateTime fechaActual = LocalDateTime.now();
        cita.setFecha(fechaActual);

        repositorioCita.save(cita);

        Cita cita1 = repositorioCita.getReferenceById(1);

        cita1.setEstado("Atendiendo");

        repositorioCita.save(cita1);

        Cita cita2 = repositorioCita.getReferenceById(1);

        assertThat(cita2.getEstado()).isEqualTo(cita1.getEstado());
    }

    @Test
    @DisplayName("Test para eliminar una cita")
    void testEliminarCita(){

        Cita cita = Cita
                .builder()
                .numTurno(1)
                .estado("Activa")
                .build();

        LocalDateTime fechaActual = LocalDateTime.now();
        cita.setFecha(fechaActual);

        Cita cita1 = repositorioCita.save(cita);

        repositorioCita.delete(cita1);

        Cita cita2 = repositorioCita.findById(cita1.getIdCita()).orElse(null);

        assertThat(cita2).isNull();
    }

    @Test
    @DisplayName("Test para eliminar una cita por su llave primaria")
    void testEliminarCitaPorPk(){

        Cita cita = Cita
                .builder()
                .numTurno(1)
                .estado("Activa")
                .build();

        LocalDateTime fechaActual = LocalDateTime.now();
        cita.setFecha(fechaActual);

        Cita cita1 = repositorioCita.save(cita);

        repositorioCita.deleteById(cita1.getIdCita());

        Cita cita2 = repositorioCita.findById(cita1.getIdCita()).orElse(null);

        assertThat(cita2).isNull();
    }
}
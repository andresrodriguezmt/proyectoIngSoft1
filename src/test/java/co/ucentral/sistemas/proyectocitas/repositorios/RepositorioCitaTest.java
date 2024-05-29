package co.ucentral.sistemas.proyectocitas.repositorios;

import co.ucentral.sistemas.proyectocitas.entidades.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RepositorioCitaTest {

    @Autowired
    RepositorioCita repositorioCita;

    @Autowired
    RepositorioCliente repositorioCliente;

    @Autowired
    RepositorioSede repositorioSede;

    @Autowired
    RepositorioServicio repositorioServicio;

    @Autowired
    RepositorioEmpleado repositorioEmpleado;

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

        Cita cita = Cita
                .builder()
                .estado("Esperando")
                .numTurno(2)
                .fecha(LocalDateTime.of(2024,6,29,8,30,0))
                .build();


        repositorioCita.save(cita);

        List<Cita> listaCitas = repositorioCita.findAll();

        if(listaCitas.isEmpty()){
            listaCitas = null;
        }

        assertThat(listaCitas).isNotNull();
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
    @DisplayName("Test para traer todas las citas por cliente")
    void testBuscarTodoPorCliente(){

        Cliente cliente = Cliente
                .builder()
                .nombre("Andres Felipe Rodriguez")
                .edad(23)
                .cedula("1005547896")
                .estado("Libre")
                .correo("rodriguez345@gmail.com")
                .contrasenia("rodriguez456")
                .build();

        Cliente cliente1 = repositorioCliente.save(cliente);

        Cita cita = Cita
                .builder()
                .estado("Activa")
                .numTurno(2)
                .cliente(cliente)
                .fecha(LocalDateTime.of(2024,5,28,10,30,0))
                .build();

        Cita cita2 = Cita
                .builder()
                .estado("Esperando")
                .numTurno(2)
                .cliente(cliente)
                .fecha(LocalDateTime.of(2024,6,29,8,30,0))
                .build();


        repositorioCita.save(cita);
        repositorioCita.save(cita2);

        List<Cita> listaCitas = repositorioCita.findAllByCliente(cliente1);

        assertThat(listaCitas).hasSize(2);

    }

    @Test
    @DisplayName("Test para buscar todos las citas por fechas")
    void testBuscarCitasPorFechas(){

        Cita cita = Cita
                .builder()
                .estado("Activa")
                .numTurno(2)
                .fecha(LocalDateTime.of(2024,5,28,10,30,0))
                .build();

        Cita cita2 = Cita
                .builder()
                .estado("Esperando")
                .numTurno(2)
                .fecha(LocalDateTime.of(2024,6,29,8,30,0))
                .build();


        repositorioCita.save(cita);
        repositorioCita.save(cita2);

        List<Cita> listaCitasFecha1 = repositorioCita.findAllByFecha(cita.getFecha());
        List<Cita> listaCitasFecha2 = repositorioCita.findAllByFecha(cita2.getFecha());

        assertThat(listaCitasFecha1).hasSize(1);
        assertThat(listaCitasFecha2).hasSize(1);

    }

    @Test
    @DisplayName("Test para traer el ultimo numero de turno de la cita por servicio y sede")
    void testTraerElUltimoNumTurno(){
        Servicio servicio = Servicio
                .builder()
                .idServicio(1)
                .nombre("Asesoria y caja")
                .build();

        repositorioServicio.save(servicio);

        Sede sede = Sede
                .builder()
                .idSede(1)
                .nombre("Zona nororiente")
                .direccion("Calle 65 #76-68 norte")
                .numEmpleado(45)
                .horaApertura(LocalTime.of(8,0,0))
                .horaCierre(LocalTime.of(16,0,0))
                .build();

        repositorioSede.save(sede);

        Cita cita = Cita
                .builder()
                .estado("Activa")
                .numTurno(2)
                .sede(sede)
                .servicio(servicio)
                .fecha(LocalDateTime.of(2024,5,28,10,30,0))
                .build();

        repositorioCita.save(cita);

        int numeroTurno = repositorioCita.findLastNumTurnoByServicioAndSede(servicio.getIdServicio(), sede.getIdSede());

        assertThat(numeroTurno).isEqualTo(2);
    }

    @Test
    @DisplayName("Test para traer una cita por fecha, por sede, por cliente y estado")
    void testTraerCitaPorFechaPorSedePorClienteyEstado(){

        Servicio servicio = Servicio
                .builder()
                .idServicio(1)
                .nombre("Asesoria y caja")
                .build();

        Servicio servicio1 = repositorioServicio.save(servicio);

        Sede sede = Sede
                .builder()
                .idSede(1)
                .nombre("Zona nororiente")
                .direccion("Calle 65 #76-68 norte")
                .numEmpleado(45)
                .horaApertura(LocalTime.of(8,0,0))
                .horaCierre(LocalTime.of(16,0,0))
                .build();

        Sede sede1 = repositorioSede.save(sede);

        Cliente cliente = Cliente
                .builder()
                .nombre("Andres Felipe Rodriguez")
                .edad(23)
                .cedula("1005547896")
                .estado("Libre")
                .correo("rodriguez345@gmail.com")
                .contrasenia("rodriguez456")
                .build();

        Cliente cliente1 = repositorioCliente.save(cliente);

        Cita cita = Cita
                .builder()
                .estado("Activa")
                .numTurno(2)
                .sede(sede)
                .servicio(servicio)
                .cliente(cliente)
                .fecha(LocalDateTime.of(2024,5,28,10,30,0))
                .build();

        repositorioCita.save(cita);

        Cita cita1 = repositorioCita.findCitaByFechaBySedeByClienteAndEstado(cita.getFecha(), sede1.getIdSede(), cliente1.getIdCliente(), cita.getEstado());

        assertThat(cita1).isNotNull();
    }

    @Test
    @DisplayName("Buscar lista de fechas en la base de datos por servicio, por sede y fecha")
    void testTraerFechasPorServicioPorSedeYFecha(){
        Servicio servicio = Servicio
                .builder()
                .idServicio(1)
                .nombre("Asesoria y caja")
                .build();

        Servicio servicio1 = repositorioServicio.save(servicio);

        Sede sede = Sede
                .builder()
                .idSede(1)
                .nombre("Zona nororiente")
                .direccion("Calle 65 #76-68 norte")
                .numEmpleado(45)
                .horaApertura(LocalTime.of(8,0,0))
                .horaCierre(LocalTime.of(16,0,0))
                .build();

        Sede sede1 = repositorioSede.save(sede);

        Cliente cliente = Cliente
                .builder()
                .nombre("Andres Felipe Rodriguez")
                .edad(23)
                .cedula("1005547896")
                .estado("Libre")
                .correo("rodriguez345@gmail.com")
                .contrasenia("rodriguez456")
                .build();

        Cliente cliente1 = repositorioCliente.save(cliente);

        Cita cita = Cita
                .builder()
                .estado("Activa")
                .numTurno(2)
                .sede(sede)
                .servicio(servicio)
                .cliente(cliente)
                .fecha(LocalDateTime.of(2024,5,28,10,30,0))
                .build();

        repositorioCita.save(cita);

        List<String> listaFechas = repositorioCita.findFechasByServicioBySedeAndFecha(servicio1.getIdServicio(),sede1.getIdSede(), cita.getFecha());

        assertThat(listaFechas).isNotNull();
    }

    @Test
    @DisplayName("Test para traer todas las citas con el mismo servicio, sede y estado")
    void testTraerTodoPorServicioPorSedeYEstado(){
        Servicio servicio = Servicio
                .builder()
                .idServicio(1)
                .nombre("Asesoria y caja")
                .build();

        Servicio servicio1 = repositorioServicio.save(servicio);

        Sede sede = Sede
                .builder()
                .idSede(1)
                .nombre("Zona nororiente")
                .direccion("Calle 65 #76-68 norte")
                .numEmpleado(45)
                .horaApertura(LocalTime.of(8,0,0))
                .horaCierre(LocalTime.of(16,0,0))
                .build();

        Sede sede1 = repositorioSede.save(sede);

        Cliente cliente = Cliente
                .builder()
                .nombre("Andres Felipe Rodriguez")
                .edad(23)
                .cedula("1005547896")
                .estado("Libre")
                .correo("rodriguez345@gmail.com")
                .contrasenia("rodriguez456")
                .build();

        Cliente cliente1 = repositorioCliente.save(cliente);

        Cita cita = Cita
                .builder()
                .estado("Activa")
                .numTurno(2)
                .sede(sede)
                .servicio(servicio)
                .cliente(cliente)
                .fecha(LocalDateTime.of(2024,5,28,10,30,0))
                .build();

        repositorioCita.save(cita);

        List<Cita> listaCitas = repositorioCita.findAllByServicioBySedeAndEstado(servicio1.getIdServicio(),sede1.getIdSede(), cita.getEstado());

        assertThat(listaCitas).hasSize(1);
    }

    @Test
    @DisplayName("Buscar todas las citas asociadas a un cliente y un estado en particular")
    void testBuscarTodoPorClienteYEstado(){

        Cliente cliente = Cliente
                .builder()
                .nombre("Andres Felipe Rodriguez")
                .edad(23)
                .cedula("1005547896")
                .estado("Libre")
                .correo("rodriguez345@gmail.com")
                .contrasenia("rodriguez456")
                .build();

        Cliente cliente1 = repositorioCliente.save(cliente);

        Cita cita = Cita
                .builder()
                .estado("Activa")
                .numTurno(2)
                .cliente(cliente)
                .fecha(LocalDateTime.of(2024,5,28,10,30,0))
                .build();

        Cita cita2 = Cita
                .builder()
                .estado("Activa")
                .numTurno(3)
                .cliente(cliente)
                .fecha(LocalDateTime.of(2024,5,28,11,30,0))
                .build();

        repositorioCita.save(cita);
        repositorioCita.save(cita2);

        List<Cita> listaCitas = repositorioCita.findAllByClienteAndEstado(cliente1.getIdCliente(), cita.getEstado());

        assertThat(listaCitas).hasSize(2);

    }

    @Test
    @DisplayName("Test para traer todo por servicio, por sede, por estado y por un empleado ")
    void testBuscarTodoPorServicioPorSedePorEstadoYEmpledo(){
        Servicio servicio = Servicio
                .builder()
                .idServicio(1)
                .nombre("Asesoria y caja")
                .build();

        Servicio servicio1 = repositorioServicio.save(servicio);

        Sede sede = Sede
                .builder()
                .idSede(1)
                .nombre("Zona nororiente")
                .direccion("Calle 65 #76-68 norte")
                .numEmpleado(45)
                .horaApertura(LocalTime.of(8,0,0))
                .horaCierre(LocalTime.of(16,0,0))
                .build();

        Sede sede1 = repositorioSede.save(sede);

        Empleado empleado = Empleado
                .builder()
                .nombre("Andres Felipe Rodriguez")
                .edad(48)
                .estado("Libre")
                .cedula("1006588974")
                .contrasenia("Rodriguez234")
                .sede(sede1)
                .servicio(servicio1)
                .build();

        Empleado empleado1 = repositorioEmpleado.save(empleado);

        Cliente cliente = Cliente
                .builder()
                .nombre("Andres Felipe Rodriguez")
                .edad(23)
                .cedula("1005547896")
                .estado("Libre")
                .correo("rodriguez345@gmail.com")
                .contrasenia("rodriguez456")
                .build();

        Cliente cliente1 = repositorioCliente.save(cliente);

        Cita cita = Cita
                .builder()
                .estado("Activa")
                .numTurno(2)
                .sede(sede)
                .servicio(servicio)
                .cliente(cliente)
                .fecha(LocalDateTime.of(2024,5,28,10,30,0))
                .build();

        repositorioCita.save(cita);

        List<Cita> listaCitas = repositorioCita.findAllByServicioBySedeByEstadoAndEmpleado(servicio1.getIdServicio(), sede1.getIdSede(), cita.getEstado(), empleado1.getIdEmpleado());

        assertThat(listaCitas).isNotNull();
    }

    @Test
    @DisplayName("Test para traer la cita asociada con un servicio, con una sede, una fecha y un estado")
    void testBuscarCitaPorServicioPorSedePorFechaYEstado(){

        Servicio servicio = Servicio
                .builder()
                .idServicio(1)
                .nombre("Asesoria y caja")
                .build();

        Servicio servicio1 = repositorioServicio.save(servicio);

        Sede sede = Sede
                .builder()
                .idSede(1)
                .nombre("Zona nororiente")
                .direccion("Calle 65 #76-68 norte")
                .numEmpleado(45)
                .horaApertura(LocalTime.of(8,0,0))
                .horaCierre(LocalTime.of(16,0,0))
                .build();

        Sede sede1 = repositorioSede.save(sede);

        Cita cita = Cita
                .builder()
                .estado("Activa")
                .numTurno(2)
                .sede(sede)
                .servicio(servicio)
                .fecha(LocalDateTime.of(2024,5,28,10,30,0))
                .build();

        repositorioCita.save(cita);

        Cita cita1 = repositorioCita.findAllByServicioBySedeByEstadoAndFecha(servicio1.getIdServicio(), sede1.getIdSede(), cita.getEstado(), cita.getFecha());
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
package co.ucentral.sistemas.proyectocitas.repositorios;

import co.ucentral.sistemas.proyectocitas.entidades.Empleado;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RepositorioEmpleadoTest {

    @Autowired
    RepositorioEmpleado repositorioEmpleado;

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    @DisplayName("Prueba para crear un empleado")
    void testCrearEmpleado(){

        Empleado empleado = Empleado
                .builder()
                .nombre("Andres Felipe Rodriguez")
                .edad(48)
                .estado("Libre")
                .cedula("1006588974")
                .contrasenia("Rodriguez234")
                .build();

        Empleado empleado1 = repositorioEmpleado.save(empleado);

        assertThat(empleado1).isNotNull();

        assertThat(empleado1.getIdEmpleado()).isPositive();
    }

    @Test
    @DisplayName("Prueba para listar empleados")
    void testListarEmpledos(){
        List<Empleado> listaEmpleados = repositorioEmpleado.findAll();

        assertThat(listaEmpleados).isNotNull();
    }

    @Test
    @DisplayName("Prueba para listar empleados, ingresando 2 empleados")
    void testListarEmpleadosConDatos(){

        Empleado empleado = Empleado
                .builder()
                .nombre("Andres Felipe Rodriguez")
                .edad(48)
                .estado("Libre")
                .cedula("1006588974")
                .contrasenia("Rodriguez234")
                .build();

        Empleado empleado1 = Empleado
                .builder()
                .nombre("Maria Isabella Gonzales Pedraza")
                .edad(35)
                .estado("Libre")
                .cedula("1069874387")
                .contrasenia("Gonzales865")
                .build();

        repositorioEmpleado.save(empleado);
        repositorioEmpleado.save(empleado1);

        List<Empleado> listasEmpleados = repositorioEmpleado.findAll();

        assertThat(listasEmpleados).isNotNull();
    }

    @Test
    @DisplayName("Test para buscar empleado por su llave primaria")
    void testBuscarEmpleadoPorPk(){

        Empleado empleado = repositorioEmpleado.getReferenceById(1);

        assertThat(empleado).isNotNull();
    }

    @Test
    @DisplayName("Test para actualizar un empleado")
    void testActualizarEmpleado(){
        Empleado empleado = repositorioEmpleado.getReferenceById(1);

        empleado.setNombre("Maria Perez Diaz");
        empleado.setEdad(34);
        empleado.setCedula("1054578234");
        empleado.setContrasenia("Perez9775");

        repositorioEmpleado.save(empleado);

        Empleado empleado1 = repositorioEmpleado.getReferenceById(1);

        assertThat(empleado1.getNombre()).isEqualTo(empleado.getNombre());
        assertThat(empleado1.getEdad()).isEqualTo(empleado.getEdad());
        assertThat(empleado1.getCedula()).isEqualTo(empleado.getCedula());
        assertThat(empleado1.getContrasenia()).isEqualTo(empleado.getContrasenia());
    }

    @Test
    @DisplayName("Test para eliminar un empleado")
    void testEliminarEmpleado(){

        Empleado empleado = Empleado
                .builder()
                .nombre("Andres Felipe Rodriguez")
                .edad(48)
                .estado("Libre")
                .cedula("1006588974")
                .contrasenia("Rodriguez234")
                .build();

        Empleado empleado1 = repositorioEmpleado.save(empleado);

        repositorioEmpleado.delete(empleado1);

        Empleado empleado2 = repositorioEmpleado.findById(empleado1.getIdEmpleado()).orElse(null);

        assertThat(empleado2).isNull();
    }

    @Test
    @DisplayName("Test para eliminar un empleado por su llave primaria")
    void testEliminarEmpleadoPorPk(){

        Empleado empleado = Empleado
                .builder()
                .nombre("Andres Felipe Rodriguez")
                .edad(48)
                .estado("Libre")
                .cedula("1006588974")
                .contrasenia("Rodriguez234")
                .build();

        Empleado empleado1 = repositorioEmpleado.save(empleado);

        repositorioEmpleado.deleteById(empleado1.getIdEmpleado());

        Empleado empleado2 = repositorioEmpleado.findById(empleado1.getIdEmpleado()).orElse(null);

        assertThat(empleado2).isNull();
    }


    @Test
    @DisplayName("Prueba para traer empleado por cedula y contraseña")
    void testBuscarEmpPorCedulayContrasenia() {

        Empleado empleado = Empleado
                .builder()
                .nombre("Andres Felipe Rodriguez")
                .edad(48)
                .estado("Libre")
                .cedula("1006588974")
                .contrasenia("Rodriguez234")
                .build();

        repositorioEmpleado.save(empleado);

        Empleado empleado1 = repositorioEmpleado.buscarEmpPorCedulayContrasenia(empleado.getCedula(), empleado.getContrasenia());

        assertThat(empleado1).isNotNull();
        assertThat(empleado1.getCedula()).isEqualTo(empleado.getCedula());
        assertThat(empleado1.getContrasenia()).isEqualTo(empleado.getContrasenia());

    }
}
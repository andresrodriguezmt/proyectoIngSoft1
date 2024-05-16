package co.ucentral.sistemas.proyectoCitasG15.repositorios;

import co.ucentral.sistemas.proyectoCitasG15.entidades.Cliente;
import co.ucentral.sistemas.proyectoCitasG15.entidades.Empleado;
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
class RepositorioClienteTest {

    @Autowired
    RepositorioCliente repositorioCliente;

    @Test
    @DisplayName("Prueba para crear un cliente")
    void testCrearCliente(){

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

        assertThat(cliente1).isNotNull();

        assertThat(cliente1.getIdCliente()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Prueba para listar clientes")
    void testListarClientes(){
        List<Cliente> listaClientes = repositorioCliente.findAll();

        assertThat(listaClientes.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("Prueba para listar clientes, ingresando 2 clientes")
    void testListarClientesConDatos(){

        Cliente cliente = Cliente
                .builder()
                .nombre("Andres Felipe Rodriguez")
                .edad(23)
                .cedula("1005547896")
                .estado("Libre")
                .correo("rodriguez345@gmail.com")
                .contrasenia("rodriguez456")
                .build();

        Cliente cliente1 = Cliente
                .builder()
                .nombre("Felipe Martinez Amadeo")
                .edad(23)
                .cedula("1095547892")
                .estado("Libre")
                .correo("martinez345@gmail.com")
                .contrasenia("martinez456")
                .build();

        repositorioCliente.save(cliente);
        repositorioCliente.save(cliente1);

        List<Cliente> listaClientes = repositorioCliente.findAll();

        assertThat(listaClientes.isEmpty()).isFalse();
        assertThat(listaClientes.size()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Test para buscar cliente por su llave primaria")
    void testBuscarClientePorPk(){

        Cliente cliente = repositorioCliente.getReferenceById(1);

        assertThat(cliente).isNotNull();
    }

    @Test
    @DisplayName("Test para actualizar un cliente")
    void testActualizarCliente(){

        Cliente cliente = repositorioCliente.getReferenceById(1);

        cliente.setNombre("Maria Perez Gutierrez");
        cliente.setEdad(34);
        cliente.setCorreo("mperezg34@gmail.com");
        cliente.setContrasenia("martinez234");

        repositorioCliente.save(cliente);

        Cliente cliente1 = repositorioCliente.getReferenceById(1);

        assertThat(cliente1.getNombre()).isEqualTo(cliente.getNombre());
        assertThat(cliente1.getEdad()).isEqualTo(cliente.getEdad());
        assertThat(cliente1.getCorreo()).isEqualTo(cliente.getCorreo());
        assertThat(cliente1.getContrasenia()).isEqualTo(cliente.getContrasenia());
    }

    @Test
    @DisplayName("Test para eliminar un cliente")
    void testEliminarCliente(){

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

        repositorioCliente.delete(cliente1);

        Cliente cliente2 = repositorioCliente.findById(cliente1.getIdCliente()).orElse(null);

        assertThat(cliente2).isNull();
    }

    @Test
    @DisplayName("Test para eliminar un cliente por su llave primaria")
    void testEliminarClientePorPk(){

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

        repositorioCliente.deleteById(cliente1.getIdCliente());

        Cliente cliente2 = repositorioCliente.findById(cliente1.getIdCliente()).orElse(null);

        assertThat(cliente2).isNull();
    }


    @Test
    @DisplayName("Prueba para traer un cliente por cedula y contrasenia")
    void testBuscarClientePorCedulayContrasenia() {

        Cliente cliente = Cliente
                .builder()
                .nombre("Andres Felipe Rodriguez")
                .edad(23)
                .cedula("1005547896")
                .estado("Libre")
                .correo("rodriguez345@gmail.com")
                .contrasenia("rodriguez456")
                .build();

        repositorioCliente.save(cliente);

        Cliente cliente1 = repositorioCliente.buscarCliePorCedulayContrasenia(cliente.getCedula(), cliente.getContrasenia());

        assertThat(cliente1).isNotNull();
        assertThat(cliente1.getCedula()).isEqualTo(cliente.getCedula());
        assertThat(cliente1.getContrasenia()).isEqualTo(cliente.getContrasenia());

    }

    @Test
    @DisplayName("Prueba para traer un cliente por correo y contrasenia")
    void testBuscarClientePorCorreoyContrasenia() {

        Cliente cliente = Cliente
                .builder()
                .nombre("Andres Felipe Rodriguez")
                .edad(23)
                .cedula("1005547896")
                .estado("Libre")
                .correo("rodriguez345@gmail.com")
                .contrasenia("rodriguez456")
                .build();

        repositorioCliente.save(cliente);

        Cliente cliente1 = repositorioCliente.buscarCliePorCorreoyContrasenia(cliente.getCorreo(), cliente.getContrasenia());

        assertThat(cliente1).isNotNull();
        assertThat(cliente1.getCorreo()).isEqualTo(cliente.getCorreo());
        assertThat(cliente1.getContrasenia()).isEqualTo(cliente.getContrasenia());

    }
}
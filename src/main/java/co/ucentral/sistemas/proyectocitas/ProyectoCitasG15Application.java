package co.ucentral.sistemas.proyectocitas;

import co.ucentral.sistemas.proyectocitas.entidades.Cliente;
import co.ucentral.sistemas.proyectocitas.entidades.Empleado;
import co.ucentral.sistemas.proyectocitas.entidades.Sede;
import co.ucentral.sistemas.proyectocitas.entidades.Servicio;
import co.ucentral.sistemas.proyectocitas.repositorios.RepositorioCliente;
import co.ucentral.sistemas.proyectocitas.repositorios.RepositorioEmpleado;
import co.ucentral.sistemas.proyectocitas.repositorios.RepositorioSede;
import co.ucentral.sistemas.proyectocitas.repositorios.RepositorioServicio;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log4j2
public class ProyectoCitasG15Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoCitasG15Application.class, args);
	}

	RepositorioServicio repositorioServicio;


	RepositorioCliente repositorioCliente;


	RepositorioEmpleado repositorioEmpleado;


	RepositorioSede repositorioSede;


	public ProyectoCitasG15Application(RepositorioServicio repositorioServicio, RepositorioCliente repositorioCliente, RepositorioEmpleado repositorioEmpleado, RepositorioSede repositorioSede) {
		this.repositorioServicio = repositorioServicio;
		this.repositorioCliente = repositorioCliente;
		this.repositorioEmpleado = repositorioEmpleado;
		this.repositorioSede = repositorioSede;
	}

	@Override
	public void run(String... args) throws Exception {

		String estado = "Nuevo";

		repositorioCliente.save(new Cliente(0, "Santiago Gonzales Garcia ",30 ,"1225489671" ,estado ,"agonzalesg@gmail.com" ,"gonzales12345"));
		repositorioCliente.save(new Cliente(0, "Felipe Quintero Pinzon",23 ,"1005688745" ,estado ,"fquinterop@gmail.com" ,"quintero456"));
		repositorioCliente.save(new Cliente(0, "Santiago Cruz",40 ,"1000475578" ,estado ,"scruz@gmail.com" ,"cruz123"));
		repositorioCliente.save(new Cliente(0, "Felipe Rodriguez Ramirez",35 ,"1003655784" ,estado ,"frodriguezr@gmail.com" ,"rodriguez987"));
		repositorioCliente.save(new Cliente(0, "Maria Alfonso Perez",25 ,"1023644789" ,estado ,"malfonsop@gmail.com" ,"alfonso675"));
		repositorioCliente.save(new Cliente(0, "Melissa Martinez Gomez",31 ,"1007855134" ,estado ,"mmartinezg@gmail.com" ,"martinez345"));
		repositorioCliente.save(new Cliente(0, "Elizabeth Torres Rodriguez",47 ,"1000533467" ,estado ,"etorresr@gmail.com" ,"torres098"));
		repositorioCliente.save(new Cliente(0, "Gonzalo Sanchez Diaz",50 ,"1000123314" ,estado ,"gsanchezd@gmail.com" ,"sanchez654"));
		repositorioCliente.save(new Cliente(0, "Nicolas Jimenez Herrera",52 ,"1000111478" ,estado ,"njimenezh@gmail.com" ,"jimenez718"));
		repositorioCliente.save(new Cliente(0, "Valentina Castro Suarez",21 ,"1009877954" ,estado ,"vcastros@gmail.com" ,"castro619"));

		Servicio servicioCaja = new Servicio();
		servicioCaja.setNombre("Caja");

		Servicio servicioAsesoria = new Servicio();
		servicioAsesoria.setNombre("Asesoría");

		Servicio servicioObtenerProd = new Servicio();
		servicioObtenerProd.setNombre("Obtener nuevos productos");

		repositorioServicio.save(servicioCaja);
		repositorioServicio.save(servicioAsesoria);
		repositorioServicio.save(servicioObtenerProd);


		Sede sede1 = new Sede();
		sede1.setNombre("Zona sur");
		sede1.setDireccion("Avenida Caracas #26A-71 Sur");
		sede1.setNumEmpleado(6);
		sede1.setHoraApertura(8,0,0);
		sede1.setHoraCierre(16,0,0);

		Sede sede2 = new Sede();
		sede2.setNombre("Zona centro");
		sede2.setDireccion("Calle 31 #13ª-51");
		sede2.setNumEmpleado(6);
		sede2.setHoraApertura(8,0,0);
		sede2.setHoraCierre(16,0,0);

		Sede sede3 = new Sede();
		sede3.setNombre("Zona norte");
		sede3.setDireccion("Carrera 15 #91-46");
		sede3.setNumEmpleado(6);
		sede3.setHoraApertura(8,0,0);
		sede3.setHoraCierre(16,0,0);

		Sede sede4 = new Sede();
		sede4.setNombre("Aeropuerto");
		sede4.setDireccion("Av El Dorado 105 39 Nuevo Muelle International De Cargo");
		sede4.setNumEmpleado(6);
		sede4.setHoraApertura(8,0,0);
		sede4.setHoraCierre(16,0,0);

		repositorioSede.save(sede1);
		repositorioSede.save(sede2);
		repositorioSede.save(sede3);
		repositorioSede.save(sede4);

		String estadoEmpleado = "Libre";

		Empleado empleadoS1 = new Empleado();
		empleadoS1.setNombre("Juan Rodriguez Perez");
		empleadoS1.setEdad(25);
		empleadoS1.setEstado(estadoEmpleado);
		empleadoS1.setCedula("1234567890");
		empleadoS1.setContrasenia("Rodriguez123");
		empleadoS1.setSede(sede1);
		empleadoS1.setServicio(servicioCaja);

		Empleado empleadoS2 = new Empleado();
		empleadoS2.setNombre("Carlos Martinez Gomez");
		empleadoS2.setEdad(34);
		empleadoS2.setEstado(estadoEmpleado);
		empleadoS2.setCedula("1045678901");
		empleadoS2.setContrasenia("Martinez234");
		empleadoS2.setSede(sede1);
		empleadoS2.setServicio(servicioCaja);

		Empleado empleadoS3 = new Empleado();
		empleadoS3.setNombre("Diana Garcia Lopez");
		empleadoS3.setEdad(45);
		empleadoS3.setEstado(estadoEmpleado);
		empleadoS3.setCedula("1046789012");
		empleadoS3.setContrasenia("Garcia345");
		empleadoS3.setSede(sede1);
		empleadoS3.setServicio(servicioCaja);

		Empleado empleadoS4 = new Empleado();
		empleadoS4.setNombre("Angela Gomez Hernandez");
		empleadoS4.setEdad(29);
		empleadoS4.setEstado(estadoEmpleado);
		empleadoS4.setCedula("1056789012");
		empleadoS4.setContrasenia("Gomez456");
		empleadoS4.setSede(sede1);
		empleadoS4.setServicio(servicioAsesoria);

		Empleado empleadoS5 = new Empleado();
		empleadoS5.setNombre("Sergio Lopez Sanchez");
		empleadoS5.setEdad(45);
		empleadoS5.setEstado(estadoEmpleado);
		empleadoS5.setCedula("1046789012");
		empleadoS5.setContrasenia("Garcia345");
		empleadoS5.setSede(sede1);
		empleadoS5.setServicio(servicioAsesoria);

		Empleado empleadoS6 = new Empleado();
		empleadoS6.setNombre("Diana García López");
		empleadoS6.setEdad(38);
		empleadoS6.setEstado(estadoEmpleado);
		empleadoS6.setCedula("1078901234");
		empleadoS6.setContrasenia("Lopez567");
		empleadoS6.setSede(sede1);
		empleadoS6.setServicio(servicioObtenerProd);

		Empleado empleadoS7 = new Empleado();
		empleadoS7.setNombre("Gonzalo Rodriguez Garcia ");
		empleadoS7.setEdad(48);
		empleadoS7.setEstado(estadoEmpleado);
		empleadoS7.setCedula("1067901234");
		empleadoS7.setContrasenia("Rodriguez567");
		empleadoS7.setSede(sede1);
		empleadoS7.setServicio(servicioObtenerProd);

		Empleado empleadoC1 = new Empleado();
		empleadoC1.setNombre("Andres Hernandez Ramirez");
		empleadoC1.setEdad(50);
		empleadoC1.setEstado(estadoEmpleado);
		empleadoC1.setCedula("1290123456");
		empleadoC1.setContrasenia("Hernandez789");
		empleadoC1.setSede(sede2);
		empleadoC1.setServicio(servicioCaja);

		Empleado empleadoC2 = new Empleado();
		empleadoC2.setNombre("Laura Sanchez Diaz");
		empleadoC2.setEdad(19);
		empleadoC2.setEstado(estadoEmpleado);
		empleadoC2.setCedula("1002234567");
		empleadoC2.setContrasenia("Sanchez890");
		empleadoC2.setSede(sede2);
		empleadoC2.setServicio(servicioCaja);

		Empleado empleadoC3 = new Empleado();
		empleadoC3.setNombre("Felipe Perez Muñoz");
		empleadoC3.setEdad(42);
		empleadoC3.setEstado(estadoEmpleado);
		empleadoC3.setCedula("1041234567");
		empleadoC3.setContrasenia("Perez901");
		empleadoC3.setSede(sede2);
		empleadoC3.setServicio(servicioCaja);

		Empleado empleadoC4 = new Empleado();
		empleadoC4.setNombre("Teresa Ramirez Rojas");
		empleadoC4.setEdad(36);
		empleadoC4.setEstado(estadoEmpleado);
		empleadoC4.setCedula("1023456789");
		empleadoC4.setContrasenia("Ramirez012");
		empleadoC4.setSede(sede2);
		empleadoC4.setServicio(servicioAsesoria);

		Empleado empleadoC5 = new Empleado();
		empleadoC5.setNombre("Gabriel Diaz Moreno");
		empleadoC5.setEdad(27);
		empleadoC5.setEstado(estadoEmpleado);
		empleadoC5.setCedula("1034506789");
		empleadoC5.setContrasenia("Diaz123");
		empleadoC5.setSede(sede2);
		empleadoC5.setServicio(servicioAsesoria);

		Empleado empleadoC6 = new Empleado();
		empleadoC6.setNombre("Isabel Torres Vargas");
		empleadoC6.setEdad(31);
		empleadoC6.setEstado(estadoEmpleado);
		empleadoC6.setCedula("1075607891");
		empleadoC6.setContrasenia("Torres234");
		empleadoC6.setSede(sede2);
		empleadoC6.setServicio(servicioObtenerProd);

		Empleado empleadoC7 = new Empleado();
		empleadoC7.setNombre("Maria Loaiza Vargas");
		empleadoC7.setEdad(31);
		empleadoC7.setEstado(estadoEmpleado);
		empleadoC7.setCedula("1025567891");
		empleadoC7.setContrasenia("Loaiza234");
		empleadoC7.setSede(sede2);
		empleadoC7.setServicio(servicioObtenerProd);

		Empleado empleadoN1 = new Empleado();
		empleadoN1.setNombre("Alejandro Alfonso Ortiz");
		empleadoN1.setEdad(45);
		empleadoN1.setEstado(estadoEmpleado);
		empleadoN1.setCedula("1256708912");
		empleadoN1.setContrasenia("Alfonso345");
		empleadoN1.setSede(sede3);
		empleadoN1.setServicio(servicioCaja);

		Empleado empleadoN2 = new Empleado();
		empleadoN2.setNombre("Patricia Rojas Jiménez");
		empleadoN2.setEdad(55);
		empleadoN2.setEstado(estadoEmpleado);
		empleadoN2.setCedula("1056780912");
		empleadoN2.setContrasenia("Rojas456");
		empleadoN2.setSede(sede3);
		empleadoN2.setServicio(servicioCaja);

		Empleado empleadoN3 = new Empleado();
		empleadoN3.setNombre("Esteban Moreno Castro");
		empleadoN3.setEdad(39);
		empleadoN3.setEstado(estadoEmpleado);
		empleadoN3.setCedula("1078901234");
		empleadoN3.setContrasenia("Moreno567");
		empleadoN3.setSede(sede3);
		empleadoN3.setServicio(servicioCaja);

		Empleado empleadoN4 = new Empleado();
		empleadoN4.setNombre("Carmen Vargas Gutierrez");
		empleadoN4.setEdad(24);
		empleadoN4.setEstado(estadoEmpleado);
		empleadoN4.setCedula("1089012345");
		empleadoN4.setContrasenia("Vargas678");
		empleadoN4.setSede(sede3);
		empleadoN4.setServicio(servicioAsesoria);

		Empleado empleadoN5 = new Empleado();
		empleadoN5.setNombre("Roberto Ortiz Valencia");
		empleadoN5.setEdad(59);
		empleadoN5.setEstado(estadoEmpleado);
		empleadoN5.setCedula("1090123456");
		empleadoN5.setContrasenia("Ortiz789");
		empleadoN5.setSede(sede3);
		empleadoN5.setServicio(servicioAsesoria);

		Empleado empleadoN6 = new Empleado();
		empleadoN6.setNombre("Sofia Jimenez Ruiz");
		empleadoN6.setEdad(31);
		empleadoN6.setEstado(estadoEmpleado);
		empleadoN6.setCedula("1001234567");
		empleadoN6.setContrasenia("Jimenez890");
		empleadoN6.setSede(sede3);
		empleadoN6.setServicio(servicioAsesoria);

		Empleado empleadoN7 = new Empleado();
		empleadoN7.setNombre("Daniela Perez Ramirez");
		empleadoN7.setEdad(35);
		empleadoN7.setEstado(estadoEmpleado);
		empleadoN7.setCedula("1091234567");
		empleadoN7.setContrasenia("Perez810");
		empleadoN7.setSede(sede3);
		empleadoN7.setServicio(servicioAsesoria);

		Empleado empleadoA1 = new Empleado();
		empleadoA1.setNombre("Lucas Mendoza Castro");
		empleadoA1.setEdad(24);
		empleadoA1.setEstado(estadoEmpleado);
		empleadoA1.setCedula("1059701912");
		empleadoA1.setContrasenia("Mendoza324");
		empleadoA1.setSede(sede4);
		empleadoA1.setServicio(servicioCaja);

		Empleado empleadoA2 = new Empleado();
		empleadoA2.setNombre("Valeria Pardo Quintero");
		empleadoA2.setEdad(37);
		empleadoA2.setEstado(estadoEmpleado);
		empleadoA2.setCedula("1012780212");
		empleadoA2.setContrasenia("Pardo625");
		empleadoA2.setSede(sede4);
		empleadoA2.setServicio(servicioCaja);

		Empleado empleadoA3 = new Empleado();
		empleadoA3.setNombre("Mateo Alvarado Cifuentes");
		empleadoA3.setEdad(39);
		empleadoA3.setEstado(estadoEmpleado);
		empleadoA3.setCedula("1038901231");
		empleadoA3.setContrasenia("Alvarado926");
		empleadoA3.setSede(sede4);
		empleadoA3.setServicio(servicioCaja);

		Empleado empleadoA4 = new Empleado();
		empleadoA4.setNombre("Camila Franco Ramirez");
		empleadoA4.setEdad(21);
		empleadoA4.setEstado(estadoEmpleado);
		empleadoA4.setCedula("1009082335");
		empleadoA4.setContrasenia("Franco227");
		empleadoA4.setSede(sede4);
		empleadoA4.setServicio(servicioAsesoria);

		Empleado empleadoA5 = new Empleado();
		empleadoA5.setNombre("Nicolas Rendon Lopez");
		empleadoA5.setEdad(40);
		empleadoA5.setEstado(estadoEmpleado);
		empleadoA5.setCedula("1001335561");
		empleadoA5.setContrasenia("Ortiz789");
		empleadoA5.setSede(sede4);
		empleadoA5.setServicio(servicioAsesoria);

		Empleado empleadoA6 = new Empleado();
		empleadoA6.setNombre("Andrea Velasco Duarte");
		empleadoA6.setEdad(55);
		empleadoA6.setEstado(estadoEmpleado);
		empleadoA6.setCedula("1000234527");
		empleadoA6.setContrasenia("Velasco829");
		empleadoA6.setSede(sede4);
		empleadoA6.setServicio(servicioObtenerProd);

		Empleado empleadoA7 = new Empleado();
		empleadoA7.setNombre("Camila Pachon Diaz");
		empleadoA7.setEdad(55);
		empleadoA7.setEstado(estadoEmpleado);
		empleadoA7.setCedula("1013234927");
		empleadoA7.setContrasenia("Pachon809");
		empleadoA7.setSede(sede4);
		empleadoA7.setServicio(servicioObtenerProd);

		repositorioEmpleado.save(empleadoS1);
		repositorioEmpleado.save(empleadoS2);
		repositorioEmpleado.save(empleadoS3);
		repositorioEmpleado.save(empleadoS4);
		repositorioEmpleado.save(empleadoS5);
		repositorioEmpleado.save(empleadoS6);
		repositorioEmpleado.save(empleadoS7);
		repositorioEmpleado.save(empleadoC1);
		repositorioEmpleado.save(empleadoC2);
		repositorioEmpleado.save(empleadoC3);
		repositorioEmpleado.save(empleadoC4);
		repositorioEmpleado.save(empleadoC5);
		repositorioEmpleado.save(empleadoC6);
		repositorioEmpleado.save(empleadoC7);
		repositorioEmpleado.save(empleadoN1);
		repositorioEmpleado.save(empleadoN2);
		repositorioEmpleado.save(empleadoN3);
		repositorioEmpleado.save(empleadoN4);
		repositorioEmpleado.save(empleadoN5);
		repositorioEmpleado.save(empleadoN6);
		repositorioEmpleado.save(empleadoN7);
		repositorioEmpleado.save(empleadoA1);
		repositorioEmpleado.save(empleadoA2);
		repositorioEmpleado.save(empleadoA3);
		repositorioEmpleado.save(empleadoA4);
		repositorioEmpleado.save(empleadoA5);
		repositorioEmpleado.save(empleadoA6);
		repositorioEmpleado.save(empleadoA7);

	}
}

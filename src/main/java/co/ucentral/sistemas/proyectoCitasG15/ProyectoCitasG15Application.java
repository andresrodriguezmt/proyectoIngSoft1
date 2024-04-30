package co.ucentral.sistemas.proyectoCitasG15;

import co.ucentral.sistemas.proyectoCitasG15.entidades.Cliente;
import co.ucentral.sistemas.proyectoCitasG15.entidades.Empleado;
import co.ucentral.sistemas.proyectoCitasG15.entidades.Sede;
import co.ucentral.sistemas.proyectoCitasG15.entidades.Servicio;
import co.ucentral.sistemas.proyectoCitasG15.repositorios.RepositorioCliente;
import co.ucentral.sistemas.proyectoCitasG15.repositorios.RepositorioEmpleado;
import co.ucentral.sistemas.proyectoCitasG15.repositorios.RepositorioSede;
import co.ucentral.sistemas.proyectoCitasG15.repositorios.RepositorioServicio;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@Log4j2
public class ProyectoCitasG15Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoCitasG15Application.class, args);
		System.out.println("Aplicacion citas iniciada");
	}

	@Autowired
	RepositorioServicio repositorioServicio;

	@Autowired
	RepositorioCliente repositorioCliente;

	@Autowired
	RepositorioEmpleado repositorioEmpleado;

	@Autowired
	RepositorioSede repositorioSede;

	@Override
	public void run(String... args) throws Exception {

		/**
		 * Creación y almacenamiento en la base de datos de 10 clientes
		 */

		Cliente cliente1 = new Cliente(0, "Santiago Gonzales Garcia ",30 ,"1225489671" ,"Nuevo" ,"agonzalesg@gmail.com" ,"gonzales12345");
		Cliente cliente2 = new Cliente(0, "Felipe Quintero Pinzon",23 ,"1005688745" ,"Nuevo" ,"fquinterop@gmail.com" ,"quintero456");
		Cliente cliente3 = new Cliente(0, "Santiago Cruz",40 ,"1000475578" ,"Nuevo" ,"scruz@gmail.com" ,"cruz123");
		Cliente cliente4 = new Cliente(0, "Felipe Rodriguez Ramirez",35 ,"1003655784" ,"Nuevo" ,"frodriguezr@gmail.com" ,"rodriguez987");
		Cliente cliente5 = new Cliente(0, "Maria Alfonso Perez",25 ,"1023644789" ,"Nuevo" ,"malfonsop@gmail.com" ,"alfonso675");
		Cliente cliente6 = new Cliente(0, "Melissa Martinez Gomez",31 ,"1007855134" ,"Nuevo" ,"mmartinezg@gmail.com" ,"martinez345");
		Cliente cliente7 = new Cliente(0, "Elizabeth Torres Rodriguez",47 ,"1000533467" ,"Nuevo" ,"etorresr@gmail.com" ,"torres098");
		Cliente cliente8 = new Cliente(0, "Gonzalo Sanchez Diaz",50 ,"1000123314" ,"Nuevo" ,"gsanchezd@gmail.com" ,"sanchez654");
		Cliente cliente9 = new Cliente(0, "Nicolas Jimenez Herrera",52 ,"1000111478" ,"Nuevo" ,"njimenezh@gmail.com" ,"jimenez718");
		Cliente cliente10 = new Cliente(0, "Valentina Castro Suarez",21 ,"1009877954" ,"Nuevo" ,"vcastros@gmail.com" ,"castro619");

		repositorioCliente.save(cliente1);
		repositorioCliente.save(cliente2);
		repositorioCliente.save(cliente3);
		repositorioCliente.save(cliente4);
		repositorioCliente.save(cliente5);
		repositorioCliente.save(cliente6);
		repositorioCliente.save(cliente7);
		repositorioCliente.save(cliente8);
		repositorioCliente.save(cliente9);
		repositorioCliente.save(cliente10);

		/**
		 * Creacion de los 3 servicios (caja, asesoria y obtener nuevos prodictos)
		 */
		Servicio servicio1 = new Servicio();
		servicio1.setNombre("Caja");

		Servicio servicio2 = new Servicio();
		servicio2.setNombre("Asesoría");

		Servicio servicio3 = new Servicio();
		servicio3.setNombre("Obtener nuevos productos");

		/**
		 * Adicion por medio del repositorio (CRUD) de la información por completo creada
		 * en referencia a los 3 servicios que ofrece el banco DINER
		 */

		repositorioServicio.save(servicio1);
		repositorioServicio.save(servicio2);
		repositorioServicio.save(servicio3);


		List<Servicio> servicioCaja = new ArrayList<>();
		servicioCaja.add(servicio1);

		List<Servicio> serviciosAsObt = new ArrayList<>();
		serviciosAsObt.add(servicio2);
		serviciosAsObt.add(servicio3);


		/**
		 * Creacion de las 4 sedes
		 */

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

		/**
		 * Creación de los 24 empleados para las 4 sedes que tiene el banco
		 *
		 * Empezando por la sede zona sur y sus 6 empleados
		 */
		Empleado empleadoS1 = new Empleado();
		empleadoS1.setNombre("Juan Rodriguez Perez");
		empleadoS1.setEdad(25);
		empleadoS1.setEstado("Libre");
		empleadoS1.setCedula("1234567890");
		empleadoS1.setContrasenia("Rodriguez123");
		empleadoS1.setSede(sede1);
		empleadoS1.setServicio(servicioCaja);

		Empleado empleadoS2 = new Empleado();
		empleadoS2.setNombre("Carlos Martinez Gomez");
		empleadoS2.setEdad(34);
		empleadoS2.setEstado("Libre");
		empleadoS2.setCedula("1045678901");
		empleadoS2.setContrasenia("Martinez234");
		empleadoS2.setSede(sede1);
		empleadoS2.setServicio(servicioCaja);

		Empleado empleadoS3 = new Empleado();
		empleadoS3.setNombre("Diana Garcia Lopez");
		empleadoS3.setEdad(45);
		empleadoS3.setEstado("Libre");
		empleadoS3.setCedula("1046789012");
		empleadoS3.setContrasenia("Garcia345");
		empleadoS3.setSede(sede1);
		empleadoS3.setServicio(servicioCaja);

		Empleado empleadoS4 = new Empleado();
		empleadoS4.setNombre("Angela Gomez Hernandez");
		empleadoS4.setEdad(29);
		empleadoS4.setEstado("Libre");
		empleadoS4.setCedula("1056789012");
		empleadoS4.setContrasenia("Gomez456");
		empleadoS4.setSede(sede1);
		empleadoS4.setServicio(serviciosAsObt);

		Empleado empleadoS5 = new Empleado();
		empleadoS5.setNombre("Sergio Lopez Sanchez");
		empleadoS5.setEdad(45);
		empleadoS5.setEstado("Libre");
		empleadoS5.setCedula("1046789012");
		empleadoS5.setContrasenia("Garcia345");
		empleadoS5.setSede(sede1);
		empleadoS5.setServicio(serviciosAsObt);

		Empleado empleadoS6 = new Empleado();
		empleadoS6.setNombre("Diana García López");
		empleadoS6.setEdad(38);
		empleadoS6.setEstado("Libre");
		empleadoS6.setCedula("1078901234");
		empleadoS6.setContrasenia("Lopez567");
		empleadoS6.setSede(sede1);
		empleadoS6.setServicio(serviciosAsObt);

		/**
		 * Creacion de los 6 empleados para la zona centro
		 */
		Empleado empleadoC1 = new Empleado();
		empleadoC1.setNombre("Andres Hernandez Ramirez");
		empleadoC1.setEdad(50);
		empleadoC1.setEstado("Libre");
		empleadoC1.setCedula("1290123456");
		empleadoC1.setContrasenia("Hernandez789");
		empleadoC1.setSede(sede2);
		empleadoC1.setServicio(servicioCaja);

		Empleado empleadoC2 = new Empleado();
		empleadoC2.setNombre("Laura Sanchez Diaz");
		empleadoC2.setEdad(19);
		empleadoC2.setEstado("Libre");
		empleadoC2.setCedula("1001234567");
		empleadoC2.setContrasenia("Sanchez890");
		empleadoC2.setSede(sede2);
		empleadoC2.setServicio(servicioCaja);

		Empleado empleadoC3 = new Empleado();
		empleadoC3.setNombre("Felipe Perez Muñoz");
		empleadoC3.setEdad(42);
		empleadoC3.setEstado("Libre");
		empleadoC3.setCedula("1041234567");
		empleadoC3.setContrasenia("Perez901");
		empleadoC3.setSede(sede2);
		empleadoC3.setServicio(servicioCaja);

		Empleado empleadoC4 = new Empleado();
		empleadoC4.setNombre("Teresa Ramirez Rojas");
		empleadoC4.setEdad(36);
		empleadoC4.setEstado("Libre");
		empleadoC4.setCedula("1023456789");
		empleadoC4.setContrasenia("Ramirez012");
		empleadoC4.setSede(sede2);
		empleadoC4.setServicio(serviciosAsObt);

		Empleado empleadoC5 = new Empleado();
		empleadoC5.setNombre("Gabriel Diaz Moreno");
		empleadoC5.setEdad(27);
		empleadoC5.setEstado("Libre");
		empleadoC5.setCedula("1034506789");
		empleadoC5.setContrasenia("Diaz123");
		empleadoC5.setSede(sede2);
		empleadoC5.setServicio(serviciosAsObt);

		Empleado empleadoC6 = new Empleado();
		empleadoC6.setNombre("Isabel Torres Vargas");
		empleadoC6.setEdad(31);
		empleadoC6.setEstado("Libre");
		empleadoC6.setCedula("1075607891");
		empleadoC6.setContrasenia("Torres234");
		empleadoC6.setSede(sede2);
		empleadoC6.setServicio(serviciosAsObt);

		/**
		 * Creacion de los 6 empleados para la zona norte
		 */
		Empleado empleadoN1 = new Empleado();
		empleadoN1.setNombre("Alejandro Alfonso Ortiz");
		empleadoN1.setEdad(45);
		empleadoN1.setEstado("Libre");
		empleadoN1.setCedula("1256708912");
		empleadoN1.setContrasenia("Alfonso345");
		empleadoN1.setSede(sede3);
		empleadoN1.setServicio(servicioCaja);

		Empleado empleadoN2 = new Empleado();
		empleadoN2.setNombre("Patricia Rojas Jiménez");
		empleadoN2.setEdad(55);
		empleadoN2.setEstado("Libre");
		empleadoN2.setCedula("1056780912");
		empleadoN2.setContrasenia("Rojas456");
		empleadoN2.setSede(sede3);
		empleadoN2.setServicio(servicioCaja);

		Empleado empleadoN3 = new Empleado();
		empleadoN3.setNombre("Esteban Moreno Castro");
		empleadoN3.setEdad(39);
		empleadoN3.setEstado("Libre");
		empleadoN3.setCedula("1078901234");
		empleadoN3.setContrasenia("Moreno567");
		empleadoN3.setSede(sede3);
		empleadoN3.setServicio(servicioCaja);

		Empleado empleadoN4 = new Empleado();
		empleadoN4.setNombre("Carmen Vargas Gutierrez");
		empleadoN4.setEdad(24);
		empleadoN4.setEstado("Libre");
		empleadoN4.setCedula("1089012345");
		empleadoN4.setContrasenia("Vargas678");
		empleadoN4.setSede(sede3);
		empleadoN4.setServicio(serviciosAsObt);

		Empleado empleadoN5 = new Empleado();
		empleadoN5.setNombre("Roberto Ortiz Valencia");
		empleadoN5.setEdad(59);
		empleadoN5.setEstado("Libre");
		empleadoN5.setCedula("1090123456");
		empleadoN5.setContrasenia("Ortiz789");
		empleadoN5.setSede(sede3);
		empleadoN5.setServicio(serviciosAsObt);

		Empleado empleadoN6 = new Empleado();
		empleadoN6.setNombre("Sofia Jimenez Ruiz");
		empleadoN6.setEdad(31);
		empleadoN6.setEstado("Libre");
		empleadoN6.setCedula("1001234567");
		empleadoN6.setContrasenia("Jimenez890");
		empleadoN6.setSede(sede3);
		empleadoN6.setServicio(serviciosAsObt);

		/**
		 * Creacion de los 6 empleados para el aeropuerto
		 */
		Empleado empleadoA1 = new Empleado();
		empleadoA1.setNombre("Lucas Mendoza Castro");
		empleadoA1.setEdad(24);
		empleadoA1.setEstado("Libre");
		empleadoA1.setCedula("1059701912");
		empleadoA1.setContrasenia("Mendoza324");
		empleadoA1.setSede(sede4);
		empleadoA1.setServicio(servicioCaja);

		Empleado empleadoA2 = new Empleado();
		empleadoA2.setNombre("Valeria Pardo Quintero");
		empleadoA2.setEdad(37);
		empleadoA2.setEstado("Libre");
		empleadoA2.setCedula("1012780212");
		empleadoA2.setContrasenia("Pardo625");
		empleadoA2.setSede(sede4);
		empleadoA2.setServicio(servicioCaja);

		Empleado empleadoA3 = new Empleado();
		empleadoA3.setNombre("Mateo Alvarado Cifuentes");
		empleadoA3.setEdad(39);
		empleadoA3.setEstado("Libre");
		empleadoA3.setCedula("1038901231");
		empleadoA3.setContrasenia("Alvarado926");
		empleadoA3.setSede(sede4);
		empleadoA3.setServicio(servicioCaja);

		Empleado empleadoA4 = new Empleado();
		empleadoA4.setNombre("Camila Franco Ramirez");
		empleadoA4.setEdad(21);
		empleadoA4.setEstado("Libre");
		empleadoA4.setCedula("1009082335");
		empleadoA4.setContrasenia("Franco227");
		empleadoA4.setSede(sede4);
		empleadoA4.setServicio(serviciosAsObt);

		Empleado empleadoA5 = new Empleado();
		empleadoA5.setNombre("Nicolas Rendon Lopez");
		empleadoA5.setEdad(40);
		empleadoA5.setEstado("Libre");
		empleadoA5.setCedula("1001335561");
		empleadoA5.setContrasenia("Ortiz789");
		empleadoA5.setSede(sede4);
		empleadoA5.setServicio(serviciosAsObt);

		Empleado empleadoA6 = new Empleado();
		empleadoA6.setNombre("Andrea Velasco Duarte");
		empleadoA6.setEdad(55);
		empleadoA6.setEstado("Libre");
		empleadoA6.setCedula("1000234527");
		empleadoA6.setContrasenia("Velasco829");
		empleadoA6.setSede(sede4);
		empleadoA6.setServicio(serviciosAsObt);



		/**
		 * Creación de las listas para la adición de las relaciones existentes en las
		 * sedes, con respecto a los empleados trabajando dentro de ellas
		 */

		/**
		 * Zona sur
		 */
		List<Empleado> sedeSurLista = new ArrayList<>();
		sedeSurLista.add(empleadoS1);
		sedeSurLista.add(empleadoS2);
		sedeSurLista.add(empleadoS3);
		sedeSurLista.add(empleadoS4);
		sedeSurLista.add(empleadoS5);
		sedeSurLista.add(empleadoS6);

		sede1.setEmpleados(sedeSurLista);


		/**
		 * Zona Centro
		 */
		List<Empleado> sedeCentroLista = new ArrayList<>();
		sedeCentroLista.add(empleadoC1);
		sedeCentroLista.add(empleadoC2);
		sedeCentroLista.add(empleadoC3);
		sedeCentroLista.add(empleadoC4);
		sedeCentroLista.add(empleadoC5);
		sedeCentroLista.add(empleadoC6);

		sede2.setEmpleados(sedeCentroLista);

		/**
		 * Zona norte
		 */
		List<Empleado> sedeNorteLista = new ArrayList<>();
		sedeNorteLista.add(empleadoN1);
		sedeNorteLista.add(empleadoN2);
		sedeNorteLista.add(empleadoN3);
		sedeNorteLista.add(empleadoN4);
		sedeNorteLista.add(empleadoN5);
		sedeNorteLista.add(empleadoN6);

		sede3.setEmpleados(sedeNorteLista);

		/**
		 * Aeropuerto
		 */
		List<Empleado> sedeAeroLista = new ArrayList<>();
		sedeAeroLista.add(empleadoA1);
		sedeAeroLista.add(empleadoA2);
		sedeAeroLista.add(empleadoA3);
		sedeAeroLista.add(empleadoA4);
		sedeAeroLista.add(empleadoA5);
		sedeAeroLista.add(empleadoA6);

		sede4.setEmpleados(sedeAeroLista);

		/**
		 * Adicion por medio del repositorio (CRUD) de la información por completo creada
		 * en referencia a las 4 sedes (sucursales) que posee el banco DINER
		 */
		repositorioSede.save(sede1);
		repositorioSede.save(sede2);
		repositorioSede.save(sede3);
		repositorioSede.save(sede4);

		/**
		 * Creación de las listas para la adición de las relaciones existentes en los
		 * servicios, con respecto a los empleados que poseen dicha labor a realizar
		 */

		/**
		 * Lista de empleados que ejercen el servicio de caja
		 */
		List<Empleado> empServicioCaja = new ArrayList<>();
		empServicioCaja.add(empleadoS1);
		empServicioCaja.add(empleadoS2);
		empServicioCaja.add(empleadoS3);
		empServicioCaja.add(empleadoC1);
		empServicioCaja.add(empleadoC2);
		empServicioCaja.add(empleadoC3);
		empServicioCaja.add(empleadoN1);
		empServicioCaja.add(empleadoN2);
		empServicioCaja.add(empleadoN3);
		empServicioCaja.add(empleadoA1);
		empServicioCaja.add(empleadoA2);
		empServicioCaja.add(empleadoA3);

		servicio1.setEmpleados(empServicioCaja);

		/**
		 * Lista de empleados que ejercen el servicio de asesoria
		 */
		List<Empleado> empServicioAse = new ArrayList<>();
		empServicioAse.add(empleadoS4);
		empServicioAse.add(empleadoS5);
		empServicioAse.add(empleadoS6);
		empServicioAse.add(empleadoC4);
		empServicioAse.add(empleadoC5);
		empServicioAse.add(empleadoC6);
		empServicioAse.add(empleadoN4);
		empServicioAse.add(empleadoN5);
		empServicioAse.add(empleadoN6);
		empServicioAse.add(empleadoA4);
		empServicioAse.add(empleadoA5);
		empServicioAse.add(empleadoA6);

		servicio2.setEmpleados(empServicioAse);

		/**
		 * Lista de empleados que ejercen el servicio de asesoria
		 */
		List<Empleado> empServicioProd = new ArrayList<>();
		empServicioProd.add(empleadoS4);
		empServicioProd.add(empleadoS5);
		empServicioProd.add(empleadoS6);
		empServicioProd.add(empleadoC4);
		empServicioProd.add(empleadoC5);
		empServicioProd.add(empleadoC6);
		empServicioProd.add(empleadoN4);
		empServicioProd.add(empleadoN5);
		empServicioProd.add(empleadoN6);
		empServicioProd.add(empleadoA4);
		empServicioProd.add(empleadoA5);
		empServicioProd.add(empleadoA6);

		servicio3.setEmpleados(empServicioProd);

		/**
		 * Adicion por medio del repositorio (CRUD) de la información por completo creada
		 * en referencia a los 3 servicios que ofrece el banco DINER
		 */

		repositorioServicio.save(servicio1);
		repositorioServicio.save(servicio2);
		repositorioServicio.save(servicio3);

		/**
		 * guardado mediante el uso de repositorio (CRUD) de la información
		 * por completo creada en referencia de los 24 empleados del banco
		 */
		repositorioEmpleado.save(empleadoS1);
		repositorioEmpleado.save(empleadoS2);
		repositorioEmpleado.save(empleadoS3);
		repositorioEmpleado.save(empleadoS4);
		repositorioEmpleado.save(empleadoS5);
		repositorioEmpleado.save(empleadoS6);
		repositorioEmpleado.save(empleadoC1);
		repositorioEmpleado.save(empleadoC2);
		repositorioEmpleado.save(empleadoC3);
		repositorioEmpleado.save(empleadoC4);
		repositorioEmpleado.save(empleadoC5);
		repositorioEmpleado.save(empleadoC6);
		repositorioEmpleado.save(empleadoN1);
		repositorioEmpleado.save(empleadoN2);
		repositorioEmpleado.save(empleadoN3);
		repositorioEmpleado.save(empleadoN4);
		repositorioEmpleado.save(empleadoN5);
		repositorioEmpleado.save(empleadoN6);
		repositorioEmpleado.save(empleadoA1);
		repositorioEmpleado.save(empleadoA2);
		repositorioEmpleado.save(empleadoA3);
		repositorioEmpleado.save(empleadoA4);
		repositorioEmpleado.save(empleadoA5);
		repositorioEmpleado.save(empleadoA6);

	}
}

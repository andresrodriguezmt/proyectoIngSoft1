package co.ucentral.sistemas.proyectoCitasG15.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "EMPLEADO")
@ToString
@Entity
public class Empleado {

    @Id
    @Column(name = "EMP_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EMPLEADO")
    @SequenceGenerator(name = "SEQ_EMPLEADO", sequenceName = "SEQ_EMPLEADO", allocationSize = 1)
    private int idEmpleado;

    @Column(name = "EMP_NOMBRE", nullable = false)
    private String nombre;

    @Column(name = "EMP_EDAD", nullable = false)
    private int edad;

    @Column(name = "EMP_ESTADO", nullable = false)
    private String estado;

    @Column(name = "EMP_CEDULA", nullable = false)
    private String cedula;

    @Column(name = "EMP_CONTRASENIA", nullable = false)
    private String contrasenia;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SED_ID")
    private Sede sede;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "servicio_empleado", joinColumns = @JoinColumn(name = "emp_id", referencedColumnName = "EMP_ID"),
            inverseJoinColumns = @JoinColumn(name = "ser_id", referencedColumnName =  "SER_ID")
    )
    private List<Servicio> servicio;

}



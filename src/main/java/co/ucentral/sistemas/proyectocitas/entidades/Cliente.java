package co.ucentral.sistemas.proyectocitas.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CLIENTE")
@ToString
@Builder
@Entity
public class Cliente implements Serializable {

    @Id
    @Column(name = "CLI_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CLIENTE")
    @SequenceGenerator(name = "SEQ_CLIENTE", sequenceName = "SEQ_CLIENTE", allocationSize = 1)
    private int idCliente;

    @Column(name = "CLI_NOMBRE", nullable = false)
    private String nombre;

    @Column(name = "CLI_EDAD", nullable = false)
    private int edad;

    @Column(name = "CLI_CEDULA", nullable = false)
    private String cedula;

    @Column(name = "CLI_ESTADO", nullable = false)
    private String estado;

    @Column(name = "CLI_CORREO", nullable = false)
    private String correo;

    @Column(name = "CLI_CONTRASENIA", nullable = false)
    private String contrasenia;

}


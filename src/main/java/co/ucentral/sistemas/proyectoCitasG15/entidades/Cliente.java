package co.ucentral.sistemas.proyectoCitasG15.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CLIENTE")
@ToString
@Entity
public class Cliente implements Serializable {

    @Id
    @Column(name = "CLI_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CLIENTE")
    @SequenceGenerator(name = "SEQ_CLIENTE", sequenceName = "SEQ_CLIENTE", allocationSize = 1)
    private long idCliente;

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

    @Column(name = "CLI_CONTRASEÑA", nullable = false)
    private String contrasenia;

}


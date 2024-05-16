package co.ucentral.sistemas.proyectoCitasG15.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SERVICIO")
@ToString
@Builder
@Entity
public class Servicio implements Serializable {

    @Id
    @Column(name = "SER_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SERVICIO")
    @SequenceGenerator(name = "SEQ_SERVICIO", sequenceName = "SEQ_SERVICIO", allocationSize = 1)
    private int idServicio;

    @Column(name = "SER_NOMBRE", nullable = false)
    private String nombre;

}


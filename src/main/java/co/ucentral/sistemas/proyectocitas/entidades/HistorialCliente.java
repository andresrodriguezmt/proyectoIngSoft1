package co.ucentral.sistemas.proyectocitas.entidades;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "HISTORIALCLIENTE")
@Builder
@ToString
@Entity
public class HistorialCliente {

    @Id
    @Column(name = "HIS_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HISTORIALCLIENTE")
    @SequenceGenerator(name = "SEQ_HISTORIALCLIENTE", sequenceName = "SEQ_HISTORIALCLIENTE", allocationSize = 1)
    private int idHistorialCliente;

    @Column(name = "HIS_OBSERVACION", nullable = false)
    private String observaciones;

    @ManyToOne
    @JoinColumn(name = "CIT_ID")
    private Cita cita;

}

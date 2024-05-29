package co.ucentral.sistemas.proyectocitas.entidadesdto;

import co.ucentral.sistemas.proyectocitas.entidades.Cita;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistorialClienteDto {

    private int idHistorialCliente;

    private String observaciones;

    private Cita cita;

}

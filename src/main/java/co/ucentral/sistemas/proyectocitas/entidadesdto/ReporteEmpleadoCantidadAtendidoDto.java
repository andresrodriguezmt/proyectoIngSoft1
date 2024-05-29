package co.ucentral.sistemas.proyectocitas.entidadesdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteEmpleadoCantidadAtendidoDto {

    private String empleado;
    private String sede;
    private String servicio;
    private long cantidadAtendido;
}

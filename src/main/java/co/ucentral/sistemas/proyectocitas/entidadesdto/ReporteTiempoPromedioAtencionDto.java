package co.ucentral.sistemas.proyectocitas.entidadesdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReporteTiempoPromedioAtencionDto {

    private String servicio;
    private String tiempoPromedio;
}

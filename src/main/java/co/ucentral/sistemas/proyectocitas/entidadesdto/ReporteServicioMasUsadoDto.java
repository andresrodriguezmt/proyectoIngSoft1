package co.ucentral.sistemas.proyectocitas.entidadesdto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteServicioMasUsadoDto {

    private String nombreSede;
    private String nombreServicio;
    private Long cantidadAtendidos;
}

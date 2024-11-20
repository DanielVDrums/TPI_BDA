package bda.tpi.vehiculos.dto;

import java.util.List;

public record VehiculoDTO(
        Integer id,
        String patente,
        String modelo,
        String marca
) {
}


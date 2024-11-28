package bda.tpi.vehiculos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PosicionDTO(
        @NotBlank(message = "La patente no puede estar en blanco")
        String patente,

        @NotNull(message = "La latitud es obligatoria")
        Double latitud,

        @NotNull(message = "La longitud es obligatoria")
        Double longitud
) {}


package bda.tpi.usuarios.dto;

import bda.tpi.usuarios.entity.Prueba;

public record DetalleIncidente(
        IncidenteDTO incidente,
        Prueba prueba
) {
}

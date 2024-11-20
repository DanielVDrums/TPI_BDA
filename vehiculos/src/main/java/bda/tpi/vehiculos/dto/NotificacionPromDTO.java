package bda.tpi.vehiculos.dto;

import java.util.List;

public record NotificacionPromDTO(
        List<String> nroTelefonos,
        String descripcion
) {
}

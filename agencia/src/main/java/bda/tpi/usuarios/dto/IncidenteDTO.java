package bda.tpi.usuarios.dto;

public record IncidenteDTO(
        Integer id,
        Integer idVehiculo,
        String patente,
        String mensaje,
        String fecha
) {
}

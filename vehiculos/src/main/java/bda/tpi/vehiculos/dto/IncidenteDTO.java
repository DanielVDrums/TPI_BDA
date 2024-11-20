package bda.tpi.vehiculos.dto;

public record IncidenteDTO(
        Integer id,
        Integer idVehiculo,
        String patente,
        String mensaje,
        String fecha
) {
}
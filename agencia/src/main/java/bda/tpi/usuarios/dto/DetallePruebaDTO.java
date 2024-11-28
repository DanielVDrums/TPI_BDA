package bda.tpi.usuarios.dto;

public record DetallePruebaDTO (
    Integer idPrueba,
    String fechaHoraInicio,
    String fechaHoraFin,
    Integer legajoEmpleado,
    String apellidoNombreEmpleado,
    Long documentoInteresado,
    String apellidoNombreInteresado,
    String comentario
) {
}

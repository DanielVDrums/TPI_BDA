package bda.tpi.usuarios.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

public record PruebaDTO(
        @NotNull(message = "El campo 'legajo' es obligatorio.") Integer legajo,
        @NotNull(message = "El campo 'vehiculoPatente' es obligatorio.") String vehiculoPatente,
        @NotNull(message = "El campo 'usuarioDni' es obligatorio.") Long usuarioDni,
        @NotNull(message = "El campo 'fechaHoraInicio' es obligatorio.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm") String fechaHoraInicio,
        @NotNull(message = "El campo 'fechaHoraFin' es obligatorio.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm") String fechaHoraFin

) {}

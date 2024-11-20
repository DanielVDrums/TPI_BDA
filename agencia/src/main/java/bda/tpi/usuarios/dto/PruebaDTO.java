package bda.tpi.usuarios.dto;

import java.sql.Date;
import java.time.LocalDateTime;

public record PruebaDTO(
        Integer legajo,
        String vehiculoPatente,
        Long usuarioDni,
        String fechaHoraInicio,
        String fechaHoraFin
) {}

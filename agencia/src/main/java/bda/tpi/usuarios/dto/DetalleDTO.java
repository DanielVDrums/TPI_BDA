package bda.tpi.usuarios.dto;

import bda.tpi.usuarios.entity.Prueba;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public record DetalleDTO(
        Integer idIncidente,
        String fechaHoraRegistro,
        String mensaje,
        String patenteVehiculo,
        Integer legajoEmpleado,
        Integer idPrueba,
        String fechaHoraInicio,
        String fechaHoraFin
) {}

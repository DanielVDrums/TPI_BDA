package bda.tpi.usuarios.dto;

import jakarta.persistence.Column;

import java.util.Date;

public record InteresadoDTO (
        String tipoDocumento,
        Long documento,
        String nombre,
        String apellido,
        Boolean restringido,
        Integer nroLicencia,
        String fechaVencimientoLicencia
){
}

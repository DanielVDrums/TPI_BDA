package bda.tpi.vehiculos.controller;

import bda.tpi.vehiculos.service.VehiculoServicio;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@RestController
@RequestMapping("/reportes/kilometros")
public class ReportesController {
    private final VehiculoServicio vehiculoServicio;

    public ReportesController(VehiculoServicio vehiculoServicio) {this.vehiculoServicio = vehiculoServicio;}

    @GetMapping("/{id}")
    public double obtenerKilometrosRecorridos(
            @PathVariable("id") Integer idVehiculo,
            @RequestParam(value = "fechaInicio") String fechaInicio,
            @RequestParam(value = "fechaFin") String fechaFin) throws ParseException, MissingServletRequestParameterException {
        if (fechaInicio == null || fechaFin == null) { throw new MissingServletRequestParameterException(fechaInicio == null ? "fechaInicio" : "fechaFin", "String");}
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date inicio = format.parse(fechaInicio);
        Date fin = format.parse(fechaFin);
        if (inicio.after(fin)){ throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fecha inicio debe ser menor a fecha fin");}
        return vehiculoServicio.calcularKilometrosRecorridos(idVehiculo, inicio, fin);
    }
}

package bda.tpi.vehiculos.controller;

import bda.tpi.vehiculos.service.VehiculoServicio;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@RestController
@RequestMapping("/reportes/kilometros")
public class ReportesController {
    private final VehiculoServicio vehiculoServicio;

    public ReportesController(VehiculoServicio vehiculoServicio) {this.vehiculoServicio = vehiculoServicio;}

    @GetMapping("/{id}")
    public double obtenerKilometrosRecorridos(
            @PathVariable("id") Integer idVehiculo,
            @RequestParam(value = "fechaInicio") String fechaInicio,
            @RequestParam(value = "fechaFin") String fechaFin) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return vehiculoServicio.calcularKilometrosRecorridos(idVehiculo, format.parse(fechaInicio), format.parse(fechaFin));
    }
}

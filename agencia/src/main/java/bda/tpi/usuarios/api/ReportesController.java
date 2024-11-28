package bda.tpi.usuarios.api;

import bda.tpi.usuarios.dto.DetalleDTO;
import bda.tpi.usuarios.dto.DetalleIncidente;
import bda.tpi.usuarios.dto.DetallePruebaDTO;
import bda.tpi.usuarios.entity.Prueba;
import bda.tpi.usuarios.service.PruebaServicio;
import bda.tpi.usuarios.service.ReporteServicio;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reportes")
public class ReportesController {
    private final ReporteServicio reporteServicio;
    private final PruebaServicio pruebaServicio;

    public ReportesController(ReporteServicio reporteServicio, PruebaServicio pruebaServicio) {
        this.reporteServicio = reporteServicio;
        this.pruebaServicio = pruebaServicio;
    }

    @GetMapping("/incidentes")
    public List<Prueba> obtenerReportesDeIncidentes(){
        return reporteServicio.obtenerPruebasConIncidentes().stream().map(DetalleIncidente::prueba).collect(Collectors.toList());
    }

    @GetMapping("/incidentes/empleado/{legajo}")
    public List<DetalleDTO> obtenerReportesDeEmpleados(@PathVariable Integer legajo){
        List<DetalleDTO> resultado =  reporteServicio.obtenerPruebasConIncidentesParaEmpleado(legajo);
        if (resultado.isEmpty()){ throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no hay incidentes para ese empleado"); }
        return resultado;
    }

    @GetMapping("/kilometros")
    public void obtenerReportesDeKilometrosPrueba(){}

    @GetMapping("/vehiculo/{patente}")
    public List<DetallePruebaDTO> obtenerReportesDeVehiculosPrueba(@PathVariable String patente) {
        return pruebaServicio.obtenerDetallePruebasPorPatente(patente);
    }
}

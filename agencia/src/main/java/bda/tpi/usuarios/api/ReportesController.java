package bda.tpi.usuarios.api;

import bda.tpi.usuarios.dto.DetalleDTO;
import bda.tpi.usuarios.dto.DetalleIncidente;
import bda.tpi.usuarios.entity.Prueba;
import bda.tpi.usuarios.service.ReporteServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

    public ReportesController(ReporteServicio reporteServicio) {
        this.reporteServicio = reporteServicio;
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

    @GetMapping("/pruebas/vehiculo/{id}")
    public void obtenerReportesDeVehiculosPrueba(){}
}

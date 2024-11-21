package bda.tpi.usuarios.api;

import bda.tpi.usuarios.entity.Prueba;
import bda.tpi.usuarios.service.ReporteServicio;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reportes")
public class ReportesController {
    private final ReporteServicio reporteServicio;

    public ReportesController(ReporteServicio reporteServicio) {
        this.reporteServicio = reporteServicio;
    }

    @GetMapping("/incidentes")
    public List<Prueba> obtenerReportesDeIncidentes(){
        return reporteServicio.obtenerPruebasConIncidentes();
    }

    @GetMapping("/empleados")
    public void obtenerReportesDeEmpleados(){}

    @GetMapping("/kilometros")
    public void obtenerReportesDeKilometrosPrueba(){}

    @GetMapping("/pruebas/vehiculo/{:id}")
    public void obtenerReportesDeVehiculosPrueba(){}
}

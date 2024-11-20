package bda.tpi.usuarios.api;

import bda.tpi.usuarios.entity.Prueba;
import bda.tpi.usuarios.service.ReporteServicio;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ADMIN') and principal.claims['preferred_username'] == 'g070-a'")
    @GetMapping("/incidentes")
    public List<Prueba> obtenerReportesDeIncidentes(){
        return reporteServicio.obtenerPruebasConIncidentes();
    }

    @PreAuthorize("hasRole('ADMIN') and principal.claims['preferred_username'] == 'g070-a'")
    @GetMapping("/empleados")
    public void obtenerReportesDeEmpleados(){}

    @PreAuthorize("hasRole('ADMIN') and principal.claims['preferred_username'] == 'g070-a'")
    @GetMapping("/kilometros")
    public void obtenerReportesDeKilometrosPrueba(){}

    @PreAuthorize("hasRole('ADMIN') and principal.claims['preferred_username'] == 'g070-a'")
    @GetMapping("/pruebas/vehiculo/{:id}")
    public void obtenerReportesDeVehiculosPrueba(){}
}

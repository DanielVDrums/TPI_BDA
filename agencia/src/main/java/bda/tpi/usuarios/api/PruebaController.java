package bda.tpi.usuarios.api;

import bda.tpi.usuarios.dto.ComentarioDTO;
import bda.tpi.usuarios.dto.PruebaDTO;
import bda.tpi.usuarios.entity.Prueba;
import bda.tpi.usuarios.service.EmpleadoService;
import bda.tpi.usuarios.service.PruebaServicio;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/pruebas")
public class PruebaController {
    private final PruebaServicio pruebaServicio;

    public PruebaController(PruebaServicio pruebaServicio) {
        this.pruebaServicio = pruebaServicio;
    }

    @GetMapping
    public ResponseEntity<?> obtenerPruebas() {
        List<Prueba> pruebas = pruebaServicio.obtenerPruebas();
        if (pruebas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay pruebas disponibles");
        } else {
            return ResponseEntity.ok(pruebas);
        }
    }
    // consigna 1.b
    @GetMapping("/enCurso")
    public ResponseEntity<?> obtenerPruebaEnCurso(@RequestParam(value = "fecha", required = false) String fecha, @RequestParam(value = "hora", required = false) String hora) throws ParseException{
        Date fechaHora;
        if (fecha == null && hora == null) {
            fechaHora = new Date();
        }else{
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            fechaHora = format.parse(fecha+" "+hora);
        }
        List<Prueba> pruebas = pruebaServicio.obtenerPruebasEnCursoPorFecha(fechaHora);
        if (pruebas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay pruebas disponibles");
        } else {
            return ResponseEntity.ok(pruebas);
        }
    }

    @PreAuthorize("hasRole('EMPLEADO') and principal.claims['preferred_username'] == 'g070-b'")
    @PostMapping("/add")
    public ResponseEntity<?> agregarPrueba(@RequestBody @Valid PruebaDTO pruebaDTO) {
        Prueba prueba = pruebaServicio.agregarNuevaPrueba(pruebaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(prueba);
    }

    @PostMapping("/empleado/{legajo}/finalizar")
    public Prueba finalizarPrueba(@PathVariable Integer legajo, @RequestBody ComentarioDTO mensaje) {
        return pruebaServicio.finalizarPruebaPorEmpleado(legajo, mensaje.comentario());
    }
}

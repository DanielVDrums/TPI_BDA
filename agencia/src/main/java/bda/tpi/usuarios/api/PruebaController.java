package bda.tpi.usuarios.api;

import bda.tpi.usuarios.dto.ComentarioDTO;
import bda.tpi.usuarios.dto.PruebaDTO;
import bda.tpi.usuarios.entity.Prueba;
import bda.tpi.usuarios.service.EmpleadoService;
import bda.tpi.usuarios.service.PruebaServicio;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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
//    Consigna 1.b. Listar todas las pruebas en curso en un momento dado
    @GetMapping("/enCurso")
    public List<Prueba> obtenerPruebaEnCurso(@RequestParam(value = "fecha", required = false) String fecha, @RequestParam(value = "hora", required = false) String hora) throws ParseException{
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date fechaHora = (fecha == null && hora == null)? new Date() : format.parse(fecha+" "+hora);
        List<Prueba> pruebas = pruebaServicio.obtenerPruebasEnCursoPorFecha(fechaHora);
        if (pruebas.isEmpty()) { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontraron pruebas"); }
        return pruebas;
    }

//    1.a. Crear una nueva prueba, validando que el cliente no tenga la licencia vencida
//    ni que esté restringido para probar vehículos en la agencia. Vamos a asumir
//    que un interesado puede tener una única licencia registrada en el sistema y
//    que todos los vehículos están patentados. También deben realizarse los
//    controles razonables del caso; por ejemplo, que un mismo vehículo no esté
//    siendo probado en ese mismo momento.
    @PostMapping("/add")
    public ResponseEntity<?> agregarPrueba(@RequestBody @Valid PruebaDTO pruebaDTO) {
        Prueba prueba = pruebaServicio.agregarNuevaPrueba(pruebaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(prueba);
    }

//    c. Finalizar una prueba, permitiéndole al empleado agregar un comentario sobre la misma.
    @PostMapping("/empleado/{legajo}/finalizar")
    public Prueba finalizarPrueba(@PathVariable Integer legajo, @RequestBody ComentarioDTO mensaje) {
        return pruebaServicio.finalizarPruebaPorEmpleado(legajo, mensaje.comentario());
    }

    @GetMapping("/empleado/{legajo}")
    public List<Prueba> obtenerPruebasPorEmpleado(@PathVariable Integer legajo) {
        List<Prueba> pruebas = pruebaServicio.buscarPruebasPorEmpleado(legajo);
        if (pruebas.isEmpty()) { throw new RuntimeException("No se encontro pruebas para el empleado "+legajo); }
        return pruebas;
    }

    @GetMapping("/empleado/{legajo}/enCurso")
    public List<Prueba> obtenerPruebasEnCursoPorEmpleado(@PathVariable Integer legajo) {
        List<Prueba> pruebas = pruebaServicio.buscarPruebasEnCursoPorEmpleado(legajo);
        if (pruebas.isEmpty()) { throw new RuntimeException("No se encontro pruebas para el empleado "+legajo); }
        return pruebas;
    }
}

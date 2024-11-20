package bda.tpi.usuarios.api;

import bda.tpi.usuarios.dto.InteresadoDTO;
import bda.tpi.usuarios.entity.Interesado;
import bda.tpi.usuarios.service.InteresadoServicio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/interesado")
public class InteresadoController {
    private final InteresadoServicio interesadoServicio;
    public InteresadoController(InteresadoServicio interesadoServicio) {
        this.interesadoServicio = interesadoServicio;
    }
    @GetMapping
    public List<Interesado> obtenerTodosInteresados(){
        return interesadoServicio.obtenerTodosLosInteresados();
    }

    @PostMapping("/add")
    public Interesado addInteresado(@RequestBody InteresadoDTO interesadoDTO){
        return interesadoServicio.agregarNuevoInteresado(interesadoDTO);
    }
}

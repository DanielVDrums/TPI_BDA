package bda.tpi.usuarios.service;

import bda.tpi.usuarios.entity.Empleado;
import bda.tpi.usuarios.repository.EmpleadoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {
    private final EmpleadoRepository empleadoRepository;
    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public List<Empleado> getAll() {return empleadoRepository.findAll();}

    public Empleado obtenerEmpleadoPorLegajo(Integer legajo) {
        Optional<Empleado> empleado = empleadoRepository.findById(legajo);
        if (empleado.isPresent()) {
            return empleado.get();
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Empleado no Encontrado");
        }
    }
}

package bda.tpi.usuarios.service;

import bda.tpi.usuarios.dto.InteresadoDTO;
import bda.tpi.usuarios.entity.Empleado;
import bda.tpi.usuarios.entity.Interesado;
import bda.tpi.usuarios.entity.Prueba;
import bda.tpi.usuarios.repository.InteresadoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InteresadoServicio {
    private final InteresadoRepository interesadoRepository;

    public InteresadoServicio(InteresadoRepository interesadoRepository) { this.interesadoRepository = interesadoRepository; }

    public Optional<Interesado> obtenerInteresadoPorId(Integer id) {
        return interesadoRepository.findById(id);
    }

    public Interesado obtenerInteresadoPorDocumento(Long documento) {
        Optional<Interesado> interesado = interesadoRepository.findByDocumento(documento);
        if (interesado.isPresent()) {
            return interesado.get();
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no Encontrado");
        }
    }

    public List<Interesado> obtenerTodosLosInteresados() {
        return interesadoRepository.findAll();
    }

    public Interesado agregarNuevoInteresado(InteresadoDTO interesadoDTO) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try{
            Date fechaHora = format.parse(interesadoDTO.fechaVencimientoLicencia());
            return interesadoRepository.save(new Interesado(
                    interesadoDTO.tipoDocumento(),
                    interesadoDTO.documento(),
                    interesadoDTO.nombre(),
                    interesadoDTO.apellido(),
                    interesadoDTO.restringido(),
                    interesadoDTO.nroLicencia(),
                    fechaHora
            ));
        }catch (ParseException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}

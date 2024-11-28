package bda.tpi.usuarios.service;

import bda.tpi.usuarios.dto.DetalleDTO;
import bda.tpi.usuarios.dto.DetalleIncidente;
import bda.tpi.usuarios.dto.IncidenteDTO;
import bda.tpi.usuarios.entity.Prueba;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReporteServicio {

    private final RestTemplate restTemplate = new RestTemplate();
    private final PruebaServicio pruebaServicio;

    public ReporteServicio(PruebaServicio pruebaServicio) {
        this.pruebaServicio = pruebaServicio;
    }

    public List<DetalleIncidente> obtenerPruebasConIncidentes() {
        try {
            ResponseEntity<List<IncidenteDTO>> response = restTemplate.exchange(
                    "http://127.0.0.1:8083/notificaciones/incidentes",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<IncidenteDTO>>() {});

            if (!response.getStatusCode().is2xxSuccessful()) { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehiculo no Encontrado");}

            List<DetalleIncidente> detalles = new ArrayList<>();
            for (IncidenteDTO incidente : response.getBody()) {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date fechaMomento = format.parse(incidente.fecha());
                Optional<Prueba> prueba = pruebaServicio.obtenerPruebaPorIdVehiculoYFecha(incidente.idVehiculo(), fechaMomento);
                prueba.ifPresent(value -> detalles.add(new DetalleIncidente(incidente, value)));
            }
            if (detalles.isEmpty()){ throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No se encontraron pruebas"); }

            return detalles;

        } catch (HttpClientErrorException e) { throw new ResponseStatusException(e.getStatusCode(), e.getMessage()); }
          catch (ParseException e) { throw new RuntimeException(e); }
    }

    public List<DetalleDTO> obtenerPruebasConIncidentesParaEmpleado(Integer legajo) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        List<DetalleIncidente> detalleIncidentes = this.obtenerPruebasConIncidentes();
        return  detalleIncidentes.stream()
                .filter(detalle -> legajo.equals(detalle.prueba().getEmpleado().getLegajo()))
                .map(detalle -> new DetalleDTO(
                        detalle.incidente().id(),
                        detalle.incidente().fecha().toString(),
                        detalle.incidente().mensaje(),
                        detalle.incidente().patente(),
                        detalle.prueba().getEmpleado().getLegajo(),
                        detalle.prueba().getId(),
                        detalle.prueba().getFechaHoraInicio().toString(),
                        detalle.prueba().getFechaHoraFin().toString()
                ))
                .collect(Collectors.toList());
    }
}

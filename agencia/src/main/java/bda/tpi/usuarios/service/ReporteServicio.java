package bda.tpi.usuarios.service;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReporteServicio {

    private final RestTemplate restTemplate = new RestTemplate();
    private final PruebaServicio pruebaServicio;

    public ReporteServicio(PruebaServicio pruebaServicio) {
        this.pruebaServicio = pruebaServicio;
    }

    public List<Prueba> obtenerPruebasConIncidentes() {
        try {
            ResponseEntity<List<IncidenteDTO>> response = restTemplate.exchange(
                    "http://127.0.0.1:8083/notificacion/incidentes",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<IncidenteDTO>>() {}
            );
            if (response.getStatusCode().is2xxSuccessful()) {
                List<Prueba> pruebas = new ArrayList<>();
                for (IncidenteDTO incidente : response.getBody()) {
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Date fechaMomento = format.parse(incidente.fecha());
                    Optional<Prueba> prueba = pruebaServicio.obtenerPruebaPorIdVehiculoYFecha(incidente.idVehiculo(), fechaMomento);
                    prueba.ifPresent(pruebas::add);
                }
                if (pruebas.isEmpty()){
                    throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No se encontraron pruebas");
                }else{
                    return pruebas;
                }
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehiculo no Encontrado");
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehiculo no Encontrado");
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al buscar el vehiculo", e);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(),e);
        }
    }
}

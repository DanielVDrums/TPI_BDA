package bda.tpi.vehiculos.service;

import bda.tpi.vehiculos.dto.IncidenteDTO;
import bda.tpi.vehiculos.dto.PosicionDTO;
import bda.tpi.vehiculos.dto.VehiculoDTO;
import bda.tpi.vehiculos.entity.Posicion;
import bda.tpi.vehiculos.entity.Vehiculo;
import bda.tpi.vehiculos.repository.VehiculoRepository;
import bda.tpi.vehiculos.API.ApiService;
import bda.tpi.vehiculos.API.ApiResponse;
import bda.tpi.vehiculos.entity.Notificacion;
import bda.tpi.vehiculos.repository.PosicionRepository;
import bda.tpi.vehiculos.repository.NotificacionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehiculoServicio {

    private final VehiculoRepository vehiculoRepository;
    private final PosicionRepository posicionRepository;
    private final NotificacionRepository notificacionRepository;
    private final ApiService apiService;

    public VehiculoServicio(
            VehiculoRepository vehiculoRepository,
            PosicionRepository posicionRepository,
            NotificacionRepository notificacionRepository,
            ApiService apiService) {
        this.vehiculoRepository = vehiculoRepository;
        this.posicionRepository = posicionRepository;
        this.notificacionRepository = notificacionRepository;
        this.apiService = apiService;
    }


    public List<Vehiculo> obtenerTodosVehiculos() {
        return vehiculoRepository.findAll();
    }

    public Optional<Vehiculo> obtenerVehiculoPorId(Integer id) {
        return vehiculoRepository.findById(id);
    }

    public void evaluarRestricciones(PosicionDTO posicionDTO) {
        Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByPatente(posicionDTO.patente());
        Vehiculo vehiculo;

        if (vehiculoOptional.isPresent()) {
            vehiculo = vehiculoOptional.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El vehículo con la patente " + posicionDTO.patente() + " no existe.");
        }

        // Crear un objeto Posicion y asociarlo al vehículo
        Posicion nuevaPosicion = new Posicion(
                vehiculo,
                new Date(),
                posicionDTO.latitud(),
                posicionDTO.longitud()
        );


        // Agregar la nueva posición a la lista de posiciones del vehículo
        vehiculo.getPosiciones().add(nuevaPosicion);

        // Guardar el vehículo actualizado y la posición en la base de datos
        vehiculoRepository.save(vehiculo);

        // Obtiene Objeto desde la API
        ApiResponse configuracion = apiService.obtenerJSON();

        // Calcula la distancia desde la agencia
        double distancia = calcularDistancia(
                configuracion.getCoordenadasAgencia().getLat(),
                configuracion.getCoordenadasAgencia().getLon(),
                posicionDTO.latitud(),
                posicionDTO.longitud()
        );

        // Validar restricciones
        if (distancia > configuracion.getRadioAdmitidoKm()) {
            generarNotificacion("El vehículo está fuera del radio permitido.", vehiculo, LocalDateTime.now());
        }

        for (ApiResponse.ZonaRestringida zona : configuracion.getZonasRestringidas()) {
            if (estaEnZonaRestringida(posicionDTO, zona)) {
                generarNotificacion("El vehículo está en una zona restringida.", vehiculo, LocalDateTime.now());
            }
        }
    }


    //calcula la distancia
    public double calcularDistancia(double lat1, double lon1, double lat2, double lon2) {
        // Diferencias de latitud y longitud
        double deltaLat = lat2 - lat1;
        double deltaLon = lon2 - lon1;

        // Ajustar la longitud por la latitud promedio
        double latitudPromedio = Math.toRadians((lat1 + lat2) / 2);
        double deltaLonAjustada = deltaLon * Math.cos(latitudPromedio);

        // Calcular distancia aproximada en km
        return 111.32 * Math.sqrt(deltaLat * deltaLat + deltaLonAjustada * deltaLonAjustada);
    }


    //valida si esta en zona
    private boolean estaEnZonaRestringida(PosicionDTO posicionDTO, ApiResponse.ZonaRestringida zona) {
        return posicionDTO.latitud() <= zona.getNoroeste().getLat() &&
                posicionDTO.latitud() >= zona.getSureste().getLat() &&
                posicionDTO.longitud() >= zona.getNoroeste().getLon() &&
                posicionDTO.longitud() <= zona.getSureste().getLon();
    }

    //genera la notificacion y guarda en BD
    private void generarNotificacion(String mensaje, Vehiculo vehiculo, LocalDateTime fechaHora) {
        Notificacion notificacion = new Notificacion();
        notificacion.setMensaje(mensaje);
        notificacion.setVehiculo(vehiculo);
        notificacion.setFechaHora(fechaHora);
        notificacionRepository.save(notificacion);
    }

    public Optional<Vehiculo> obtenerVehiculoPorPatente(String patente) {
        return vehiculoRepository.findByPatente(patente);
    }

    public double calcularKilometrosRecorridos(Integer idVehiculo, Date fechaInicio, Date fechaFin) {

        List<Posicion> posiciones = posicionRepository.buscarPorVehiculoYFechas(idVehiculo, fechaInicio, fechaFin);

        if (posiciones.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontraron posiciones para el vehículo con ID " + idVehiculo);
        }

        // Calcular la distancia total recorrida
        double distanciaTotal = 0.0;
        for (int i = 1; i < posiciones.size(); i++) {
            Posicion anterior = posiciones.get(i - 1);
            Posicion actual = posiciones.get(i);
            distanciaTotal += calcularDistancia(
                    anterior.getLatitud(), anterior.getLongitud(),
                    actual.getLatitud(), actual.getLongitud()
            );
        }

        return distanciaTotal;
    }


}

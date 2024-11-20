package bda.tpi.vehiculos.service;

import bda.tpi.vehiculos.dto.NotificacionPromDTO;
import bda.tpi.vehiculos.entity.Notificacion;
import bda.tpi.vehiculos.entity.NotificacionProm;
import bda.tpi.vehiculos.repository.NotificacionPromRepository;
import bda.tpi.vehiculos.repository.NotificacionRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NotificacionServicio {

    private final NotificacionRepository notificacionRepository;
    private final NotificacionPromRepository notificacionPromRepository;

    public NotificacionServicio(NotificacionRepository notificacionRepository, NotificacionPromRepository notificacionPromRepository) {
        this.notificacionRepository = notificacionRepository;
        this.notificacionPromRepository = notificacionPromRepository;
    }

    public List<Notificacion> obtenerVehiculoConIncidente() {
        return notificacionRepository.findAll();
    }

    public void enviarNotificacionesPromocion(NotificacionPromDTO notificacionPromDTO) {
        for (String nroTelefono : notificacionPromDTO.nroTelefonos()) {


            NotificacionProm notificacion = new NotificacionProm();
            notificacion.setNroTelefono(nroTelefono);
            notificacion.setDescripcion(notificacionPromDTO.descripcion());
            notificacion.setFechaEnvio(new Date());

            notificacionPromRepository.save(notificacion);

            System.out.println("Notificación enviada a " + nroTelefono + ": " + notificacionPromDTO.descripcion());
        }
    }




}

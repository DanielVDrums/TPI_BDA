package bda.tpi.vehiculos.repository;

import bda.tpi.vehiculos.entity.Notificacion;
import bda.tpi.vehiculos.entity.NotificacionProm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {
}

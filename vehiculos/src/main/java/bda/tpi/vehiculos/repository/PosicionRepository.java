package bda.tpi.vehiculos.repository;

import bda.tpi.vehiculos.entity.Posicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PosicionRepository extends JpaRepository<Posicion, Integer> {
    @Query(
            "SELECT p FROM Posicion p WHERE p.vehiculo.id = :idVehiculo AND p.fecha_hora >= :fechaInicio AND p.fecha_hora <= :fechaFin"
    )
      List<Posicion> buscarPorVehiculoYFechas(@Param("idVehiculo") Integer idVehiculo, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);



//    @Query("SELECT p FROM Posicion p WHERE p.vehiculo.id = :idVehiculo AND p.fecha_hora BETWEEN :fechaInicio AND :fechaFin")
//    List<Posicion> buscarPorVehiculoYFechas(@Param("idVehiculo") Integer idVehiculo,
//                                            @Param("fechaInicio") Date fechaInicio,
//                                            @Param("fechaFin") Date fechaFin);

}




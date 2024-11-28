package bda.tpi.usuarios.repository;

import bda.tpi.usuarios.entity.Empleado;
import bda.tpi.usuarios.entity.Prueba;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PruebaRepository extends JpaRepository<Prueba, Integer> {
    @Query(
            value = "SELECT p FROM Prueba p WHERE :fhc BETWEEN p.fechaHoraInicio AND p.fechaHoraFin"
    )
    public List<Prueba> findPruebasEnCursoByFecha(@Param("fhc") Date fecha);

    @Query(
            value = "SELECT p FROM Prueba p WHERE p.fechaHoraFin >= :fhc AND p.fechaHoraInicio <= :fhc AND p.idVehiculo = :idVehiculo"
    )
    public Optional<Prueba> findPruebaByIdVehiculoYFecha(@Param("idVehiculo") Integer idVehiculo, @Param("fhc") Date fechaIngresada);

    @Query(
            value = "SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Prueba p WHERE p.fechaHoraFin >= :fhc AND p.fechaHoraInicio <= :fhc AND p.idVehiculo = :idVehiculo"
    )
    public boolean existsPruebaByIdVehiculoAndFecha(@Param("idVehiculo") Integer idVehiculo, @Param("fhc") Date fechaIngresada);

    public List<Prueba> findPruebasByEmpleado(Empleado empleado);

    @Query(
            value = "SELECT p FROM Prueba p WHERE p.empleado = :empleado AND :fecha BETWEEN p.fechaHoraInicio AND p.fechaHoraFin"
    )
    public List<Prueba> findPruebasByEmpleadoAndFecha(@Param("empleado") Empleado empleado, @Param("fecha") Date fecha);

}

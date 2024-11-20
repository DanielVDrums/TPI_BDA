package bda.tpi.usuarios.repository;

import bda.tpi.usuarios.entity.Prueba;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PruebaRepository extends JpaRepository<Prueba, Integer> {
    @Query(
            value = "SELECT p FROM Prueba p WHERE p.fechaHoraFin >= :fhc AND p.fechaHoraInicio <= :fhc"
    )
    public List<Prueba> findPruebasEnCursoByFecha(@Param("fhc") Date fecha);

    @Query(
            value = "SELECT p FROM Prueba p WHERE p.fechaHoraFin >= :fhc AND p.fechaHoraInicio <= :fhc AND p.idVehiculo = :idVehiculo"
    )
    public Optional<Prueba> findPruebaByIdVehiculoYFecha(@Param("idVehiculo") Integer idVehiculo, @Param("fhc") Date fechaIngresada);

    public Optional<Prueba> findByIdVehiculo(Integer idVehiculo);
}

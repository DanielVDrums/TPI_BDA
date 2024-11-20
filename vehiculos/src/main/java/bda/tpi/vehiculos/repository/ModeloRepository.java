package bda.tpi.vehiculos.repository;

import bda.tpi.vehiculos.entity.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModeloRepository extends JpaRepository<Modelo, Integer> {
}

